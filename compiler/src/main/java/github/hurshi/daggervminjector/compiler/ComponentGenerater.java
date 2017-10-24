package github.hurshi.daggervminjector.compiler;

import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

/**
 * Created by gavin on 2017/10/18.
 */

public class ComponentGenerater {
    public String packageName;
    private String className;
    private String moduleClassName;
    private String scopeStr;

    public InjectBean injectBean = new InjectBean();

    public ComponentGenerater(Elements util, TypeElement element, String moduleName, String scopeStr) {
        packageName = util.getPackageOf(element).getQualifiedName().toString();
        String viewModuleClassName = getClassName(element, packageName);
        className = viewModuleClassName + "_GpSubcomponent";
        moduleClassName = moduleName;
        this.scopeStr = scopeStr;

        injectBean.setSubcomponentClassName(packageName + "." + className);
        injectBean.setVmClassName(packageName + "." + viewModuleClassName);
    }

    public String generateJavaCode() {
        StringBuilder builder = new StringBuilder();
        builder.append("package ").append(packageName).append(";\n\n")
                .append("import dagger.Subcomponent;\n")
                .append("import dagger.android.AndroidInjector;\n")
                .append("import github.hurshi.daggervminjector.annotation.*;\n")
                .append("\n");

        builder.append(scopeStr)
                .append("\n")
                .append("@Subcomponent(modules = ")
                .append(moduleClassName)
                .append(".class)\n")
                .append("public interface ")
                .append(className)
                .append(" extends AndroidInjector<")
                .append(injectBean.getVmClassName())
                .append("> {");

        builder.append("\n")
                .append("    @Subcomponent.Builder\n")
                .append("    abstract class Builder extends AndroidInjector.Builder<")
                .append(injectBean.getVmClassName())
                .append("> {\n    }\n");
        builder.append("}");

        return builder.toString();
    }


    private String getClassName(TypeElement element, String packageName) {
        int packageLen = packageName.length() + 1;
        return element.getQualifiedName().toString().substring(packageLen)
                .replace('.', '$');
    }
}
