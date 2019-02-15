package github.hurshi.daggervminjector.compiler;

import java.util.List;

/**
 * Created by gavin on 2017/10/18.
 */

public class DaggerVMModuleGenerater {

    private List<InjectBean> injectBeans;
    public String packageName;
    public String className = "_GPDaggerVMInjectModule";

    public DaggerVMModuleGenerater(List<InjectBean> injectBeans, String packageName) {
        this.injectBeans = injectBeans;
        this.packageName = packageName;
    }

    public String generateJavaCode() {
        StringBuilder builder = new StringBuilder();
        builder.append("package ").append(packageName).append(";\n\n")
                .append("import dagger.Binds;\n")
                .append("import dagger.Module;\n")
                .append("import dagger.android.AndroidInjector;\n")
                .append("import dagger.multibindings.IntoMap;\n")
                .append("import github.hurshi.daggervminjector.extension.DaggerVM;\n")
                .append("import github.hurshi.daggervminjector.extension.DaggerVMKey;\n\n");

        builder.append("@Module(subcomponents = {")
                .append(getSubcomponents())
                .append("})\n");
        builder.append("public abstract class ")
                .append(className)
                .append(" {");
        for (InjectBean injectBean : injectBeans) {
            builder.append(getMethod(injectBean));
        }

        builder.append("}");
        return builder.toString();
    }

    private String getSubcomponents() {
        String string = "";
        for (InjectBean s : injectBeans) {
            string += (s.getSubcomponentClassName() + ".class,");
        }
        return string.substring(0, string.length() - 1);
    }

    private String getMethod(InjectBean injectBean) {
        StringBuilder builder = new StringBuilder();
        builder.append("\n@Binds\n")
                .append("@IntoMap\n")
                .append("@DaggerVMKey(")
                .append(injectBean.getVmClassName())
                .append(".class)\n")
                .append("abstract AndroidInjector.Factory<?> ")
                .append(injectBean.getVmClassName().replace(".", "_"))
                .append("(")
                .append(injectBean.getSubcomponentClassName())
                .append(".Builder builder);\n\n");

        return builder.toString();
    }


}
