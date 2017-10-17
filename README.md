# DaggerVMInjector
It's tedious to build write code like this ?

```

@Module(subcomponents = arrayOf(DemoSubcomponent::class))
abstract class VMInjectModule {
    @Binds
    @IntoMap
    @DaggerVMKey(DemoActivity::class)
    internal abstract fun inject2DemoActivity(builder: DemoSubcomponent.Builder): AndroidInjector.Factory<out DaggerVM>
}



@ActivityScope
@Subcomponent(modules = DemoActivityModule.class)
public interface SecondVMSubcomponent extends AndroidInjector<DemoActivity> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<DemoActivity> {
    }
}


```

and I did this for you, you can implementation the same function like this:

```
@DaggerVMModule(DemoActivityModule.class) // add this line
class DemoActivity {
	
}

```
**How convenient**
