package hurshi.github.com.daggervminjector_compiler;


import com.google.auto.common.SuperficialValidation;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;

import java.lang.annotation.Annotation;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import hurshi.github.com.daggervminjector_annotations.DaggerVMModule;

import static com.google.auto.common.MoreElements.getPackage;

@AutoService(Processor.class)
public class DaggerVMInjectorProcessor extends AbstractProcessor {

    private Elements elementUtils;
    private Types typeUtils;
    private Filer filer;
//    private Trees trees;

    @Override
    public synchronized void init(ProcessingEnvironment env) {
        super.init(env);
        elementUtils = env.getElementUtils();
        typeUtils = env.getTypeUtils();
        filer = env.getFiler();
//        try {
//            trees = Trees.instance(processingEnv);
//        } catch (IllegalArgumentException ignored) {
//        }
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
        for (Element element : env.getElementsAnnotatedWith(DaggerVMModule.class)) {
//            if (!SuperficialValidation.validateElement(element)) continue;
            try {
                TypeElement typeElement = (TypeElement) element.getEnclosingElement();
                ClassName className = getSubcomponentClassName(element, typeElement);
                System.out.println(">>>  package name = " + className.packageName());
                System.out.println(">>>  simple name = " + className.simpleName());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return false;
    }


    private ClassName getSubcomponentClassName(Element element, TypeElement enclosingElement) {
        String packageName = getPackage(element).getQualifiedName().toString();
        String className = enclosingElement.getQualifiedName().toString().substring(
                packageName.length() + 1).replace('.', '$');
        return ClassName.get(packageName, className + "Subcomponent");
    }


    private Set<Class<? extends Annotation>> getSupportedAnnotations() {
        Set<Class<? extends Annotation>> annotations = new LinkedHashSet<>();
        annotations.add(DaggerVMModule.class);

        return annotations;
    }
}
