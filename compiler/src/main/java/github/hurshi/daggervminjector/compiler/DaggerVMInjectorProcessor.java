package github.hurshi.daggervminjector.compiler;


import com.google.auto.service.AutoService;

import java.io.IOException;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.JavaFileObject;

import github.hurshi.daggervminjector.annotation.BindVMModule;


@AutoService(Processor.class)
public class DaggerVMInjectorProcessor extends AbstractProcessor {

    private Elements elementUtils;
    private Filer filer;
    private String vmPackageName;

    @Override
    public synchronized void init(ProcessingEnvironment env) {
        super.init(env);
        elementUtils = env.getElementUtils();
        filer = env.getFiler();
    }

    @Override
    public Set<String> getSupportedOptions() {
        return super.getSupportedOptions();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        for (Class<? extends Annotation> annotation : getSupportedAnnotations()) {
            types.add(annotation.getCanonicalName());
        }
        return types;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> elementsSet, RoundEnvironment env) {
        List<InjectBean> list = new ArrayList<>();
        List<InjectBean> annoList = processAnno(env, BindVMModule.class);
        if (null != annoList && annoList.size() > 0) {
            list.addAll(annoList);
        }
        if (list.size() > 0) {
            generateDaggerVMMoudle(list);
        }
        return false;
    }

    private List<InjectBean> processAnno(RoundEnvironment env, Class<? extends Annotation> var1) {
        Set<? extends Element> elements = env.getElementsAnnotatedWith(var1);

        if (null == elements || elements.size() <= 0) {
            return null;
        }
        List<InjectInfo> classNames = new ArrayList<>();
        for (Element element : elements) {
            try {
                for (AnnotationMirror mirror : element.getAnnotationMirrors()) {
                    InjectInfo injectInfo = new InjectInfo(element);
                    for (Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry : mirror.getElementValues().entrySet()) {
                        String key = entry.getKey().getSimpleName().toString();
                        Object value = entry.getValue().getValue();
                        if ("module".equals(key)) {
                            injectInfo.setModule(value.toString());
                        } else if ("scope".equals(key)) {
                            injectInfo.setScope(value.toString());
                        }
                    }
                    if (null != injectInfo.getModule() && null != injectInfo.getScope()) {
                        classNames.add(injectInfo);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (null != classNames && classNames.size() > 0) {
            List<InjectBean> injectBeans = new ArrayList<>();
            Writer writer = null;
            for (InjectInfo injectInfo : classNames) {
                TypeElement typeElement = (TypeElement) injectInfo.getElement();
                ComponentGenerater componentGenerater = new ComponentGenerater(elementUtils, typeElement, injectInfo.getModule(), injectInfo.getScope());
                injectBeans.add(componentGenerater.injectBean);
                vmPackageName = componentGenerater.packageName;
                try {
                    JavaFileObject file = filer.createSourceFile(componentGenerater.injectBean.getSubcomponentClassName(), injectInfo.getElement());
                    writer = file.openWriter();
                    writer.write(componentGenerater.generateJavaCode());
                    writer.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (writer != null) {
                        try {
                            writer.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            return injectBeans;
        }
        return null;
    }

    private void generateDaggerVMMoudle(List<InjectBean> injectBeans) {
        DaggerVMModuleGenerater daggerVMModuleGenerater = new DaggerVMModuleGenerater(injectBeans, vmPackageName);
        Writer writer = null;
        try {
            JavaFileObject file = filer.createSourceFile(daggerVMModuleGenerater.packageName + "." + daggerVMModuleGenerater.className);
            writer = file.openWriter();
            writer.write(daggerVMModuleGenerater.generateJavaCode());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Set<Class<? extends Annotation>> getSupportedAnnotations() {
        Set<Class<? extends Annotation>> annotations = new LinkedHashSet<>();
        annotations.add(BindVMModule.class);

        return annotations;
    }
}
