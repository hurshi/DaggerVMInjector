package github.hurshi.daggervminjector.compiler;

/**
 * Created by gavin on 2017/10/18.
 */

public class InjectBean {
    private String subcomponentClassName;
    private String vmClassName;

    public String getSubcomponentClassName() {
        return subcomponentClassName;
    }

    public void setSubcomponentClassName(String subcomponentClassName) {
        this.subcomponentClassName = subcomponentClassName;
    }

    public String getVmClassName() {
        return vmClassName;
    }

    public void setVmClassName(String vmClassName) {
        this.vmClassName = vmClassName;
    }
}
