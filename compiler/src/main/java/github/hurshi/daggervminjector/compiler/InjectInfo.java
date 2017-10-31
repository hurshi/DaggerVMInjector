package github.hurshi.daggervminjector.compiler;

import javax.lang.model.element.Element;

/**
 * Created by gavin on 2017/10/31.
 */

public class InjectInfo {
    private String module;
    private String scope;
    private Element element;

    public InjectInfo(Element element) {
        this.element = element;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public Element getElement() {
        return element;
    }

    public void setElement(Element element) {
        this.element = element;
    }
}
