# DaggerVMInjector
It's tedious to write code like this in dagger?

```

@Module(subcomponents = arrayOf(DemoSubcomponent::class))
abstract class DemoActivityInjectModule {
    @Binds
    @IntoMap
    @DaggerVMKey(DemoActivity::class)
    internal abstract fun inject2DemoActivity(builder: DemoSubcomponent.Builder): AndroidInjector.Factory<out DaggerVM>
}


@ActivityScope
@Subcomponent(modules = DemoActivityModule::class)
public interface DemoActivitySubcomponent extends AndroidInjector<DemoActivity> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<DemoActivity> {
    }
}

```

and I did this for you, you can implementation the same function like this:

```
@DaggerVMModule(DemoActivityModule.class) // add this line
class DemoActivity : Activity {
	
}
```
**How convenient**
