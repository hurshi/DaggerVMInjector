## Dagger扩展组件

### extension组件
##### 概述
`dagger.android`提供了注入的便捷方式，但是只能注入到`Activity`,`Fragment`等组件中，对于`MVP`或者`MVVM`框架来说，更加希望能注入到`Presenter`或者`ViewModle`中。`extension`组件扩展了`dagger.android`中的`support`组件，能注入到自定义类中。（因为本人是在`MVVM`中使用，此组件为`MVVM`设计）
##### 使用
1. 添加jitpack仓库

	```
	maven { url 'https://jitpack.io'
	```
2. 添加依赖

	```
	implementation "com.github.hurshi.dagger-vm-injector:extension:last_version"
	```
3. 修改application

	```
	public class YourApplication extends Application implements HasDaggerVMInjector {

		@Inject
    	DispatchingAndroidInjector<DaggerVM> daggerVMDispatchingAndroidInjector;

    	@Override
    	public AndroidInjector<DaggerVM> daggerVMInjector() {
       	     return daggerVMDispatchingAndroidInjector;
    	}
	}
	```
4. 在`component`中将`AndroidSupportInjectionModule`或者`AndroidInjectionModule `替换成`AndroidDaggerVMInjectionModule `

	```
	@Singleton
   @Component(modules = {AndroidDaggerVMInjectionModule.class})
   public interface AppComponent {
   	     void inject(App app);
   }

	```
5. 如果你用到`@ ActivityKey `/`@FragmentKey`，都替换成`@DaggerVMKey `
6. 注入方式：(被注入的类需要`implements DaggerVM`)

	```
	class Test implements DaggerVM{
    	public Test(Application application){
        	AndroidDaggerVMInjection.inject(application, this);
   		}
 	}
	```


### 自动绑定组件
##### 概述
上述编写module,subcomponent代码稍显复杂，这里提供自动生成这些代码的功能，实现ViewModel和module直接绑定

* **存在问题**：因为生成的代码是需要dagger去解析的，但是注解不能指定解析顺序（必须先解析我的注解，再解析dagger的注解才行），导致如果本注解比dagger的注解后执行的话，就不能生成对应的dagger代码。
* **解决方案** 把生成的代码打成jar包，提供给dagger解析。

##### 配置
1. 添加依赖

	```
	implementation "com.github.hurshi.dagger-vm-injector:extension:last_version"
	```
2. 在`module`的`build.gradle`中添加

	```
	task daggerModuleSourceJar(type: Jar) {
    	from fileTree(dir: "${project.projectDir.absolutePath}/build/intermediates/classes/debug", include: '**/_GPDaggerVMInjectModule.class')
    	from fileTree(dir: "${project.projectDir.absolutePath}/build/intermediates/classes/debug", include: '**/*_GpSubcomponent.class')
    	from fileTree(dir: "${project.projectDir.absolutePath}/build/intermediates/classes/debug", include: '**/*_GpSubcomponent$Builder.class')
}
task toCopyJars(type: Copy) {
    	from 'build/libs'
	    into 'libs_compileonly'
   		rename { String fileName ->
      		fileName.replace(".jar", "DaggerInjections.jar")
    	}
}
dependencies {
    	compileOnly fileTree(dir: 'libs_compileonly', include: ['*.jar'])
    	implementation "com.github.hurshi.dagger-vm-injector:annotation:last_version"
    	//kapt "com.github.hurshi.dagger-vm-injector:compiler:last_version"
    	annotationProcessor "com.github.hurshi.dagger-vm-injector:compiler:last_version"
}

	```
3. 项目根目录添加`shells`文件夹,在shells中添加脚本`inject.sh`

	```
	../gradlew clean -p ../
	../gradlew assembleDebug -p ../
	../gradlew daggerModuleSourceJar -p ../
	../gradlew toCopyJars -p ../
	../gradlew clean -p ../
	../gradlew assembleDebug -p ../
	```


##### 使用
1. 给`viewmodel`绑定`module`

	```
	@TargetActivityModule(MainModule.class)
	class Test implements DaggerVM{
   		public MainViewModel(Application application){
       		AndroidDaggerVMInjection.inject(application, this);
    	}
	}
	```
2. `cd shells -> sh inject.sh`
3. 确保`component`中`@Component -> modules` 中有 `_GPDaggerVMInjectModule.class`

	```
	@Singleton
   @Component(modules = {AndroidDaggerVMInjectionModule.class,_GPDaggerVMInjectModule.class})
   public interface AppComponent {
   	     void inject(App app);
   }
   ```

4.`ReBuild`后就好了





