package io.github.jianzhichun.spring.cloud.config.mongodb;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import org.springframework.beans.factory.config.YamlProcessor;
import org.springframework.cloud.config.environment.Environment;
import org.springframework.cloud.config.environment.PropertySource;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.config.server.environment.EnvironmentRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.jianzhichun.spring.cloud.config.mongodb.domain.MongoPropertySource;
import io.github.jianzhichun.spring.cloud.config.mongodb.domain.MongoPropertySourceRepository;

public interface MongodbConfigServer {
	
	@RestController
	@RequestMapping("/api")
	interface MongoConfigServerController{}
	@org.springframework.context.annotation.Configuration
	@EnableMongoRepositories(basePackages = {"io.github.jianzhichun.spring.cloud.config.mongodb.domain"})
	class Configuration{
		@Bean MongoConfigServerController mongoConfigServerController(MongoPropertySourceRepository repository){
			return new MongoConfigServerController() {
				public @PutMapping("/config/mongo") List<MongoPropertySource> saveConfigs(@RequestBody List<MongoPropertySource> sources){
					return repository.save(sources);
				}
				public @GetMapping("/config/mongo") Map<String,Map<String,List<String>>> getConfigs(){
					return repository.findAll().stream()
							.collect(
								groupingBy(
									MongoPropertySource::getApp
									, mapping(
										Function.identity()
										, groupingBy(
											MongoPropertySource::getLabel
											, mapping(MongoPropertySource::getProfile, toList())))));
				}
				public @GetMapping("/config/mongo/{id}") MongoPropertySource getConfigById(@PathVariable("id") String id){
					return repository.findById(id);
				}
				public @DeleteMapping("/config/mongo/{id}") List<MongoPropertySource> deleteConfigById(@PathVariable("id") String id){
					return repository.deleteById(id);
				}
			};
		}
		@Bean EnvironmentRepository environmentRepository(final MongoPropertySourceRepository repository) {
			Function<Map<String,Object>, Map<String,Object>> flatten = new YamlProcessor(){
				public Function<Map<String, Object>, Map<String, Object>> getFlattenFunction() {
					return new Function<Map<String,Object>, Map<String,Object>>() {
						public @Override Map<String, Object> apply(Map<String, Object> source) {
							return getFlattenedMap(source);
						} 
					};
				}
			}.getFlattenFunction();
			return new EnvironmentRepository(){
				public Environment findOne(String application, String profile, String label) {
					String[] profiles = StringUtils.commaDelimitedListToStringArray(profile);
					Environment environment = new Environment(application, profiles, label, null, null);
					List<MongoPropertySource> sources = newArrayList();
					Set<String> bf = newHashSet();
					repository.findByAppAndLabelAndProfileIn(application, label, profiles)
							.forEach(source -> generateSources(sources, source, bf));
					sources.stream().sorted().forEach(source->{
						String sourceName = generateSourceName(source);
						Map<String, Object> flatSource = flatten.apply(source.getSource());
						PropertySource propSource = new PropertySource(sourceName, flatSource);
						environment.add(propSource);
					});
					return environment;
				}
				private void generateSources(List<MongoPropertySource> sources, MongoPropertySource source, Set<String> bf){
					sources.add(source);
					if(!CollectionUtils.isEmpty(source.getIncludes())){
						source.getIncludes().forEach(include -> {
							if(bf.add(include)){
								generateSources(sources, repository.findById(include), bf);
							}
						});
					}
				}
				private String generateSourceName(MongoPropertySource source) {
					String sourceName;
					String app = source.getApp();
					String profile = source.getProfile();
					String label = source.getLabel();
					if (label != null) {
						sourceName = String.format("%s-%s-%s", app, profile, label);
					}
					else {
						sourceName = String.format("%s-%s", app, profile);
					}
					return sourceName;
				}
			};
		}
	}
	@Target(ElementType.TYPE)
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	@Import(Configuration.class)
	@EnableConfigServer
	@interface EnableMongoConfigServer {}
}
