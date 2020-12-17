package org.alee.component.preloader.compiler;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.WildcardTypeName;

import org.alee.component.compiler.BaseProcessor;
import org.alee.component.compiler.Constant;
import org.alee.component.compiler.util.CollectionUtils;
import org.alee.component.compiler.util.MapUtils;
import org.alee.component.compiler.util.StringUtils;
import org.alee.component.compiler.util.UtilFactory;
import org.alee.component.compiler.util.WriteJavaUtil;
import org.alee.component.preloader.annotation.InitialMark;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedOptions;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

import static javax.lang.model.element.Modifier.PUBLIC;

/**********************************************************
 *
 * @author: MY.Liu
 * @date: 2020/12/7
 * @description: xxxx
 *
 *********************************************************/
@AutoService(Processor.class)
@SupportedOptions(Constant.MODULE_NAME_KEY)
@SupportedSourceVersion(SourceVersion.RELEASE_7)
@SupportedAnnotationTypes({InitialMarkProcessor.ANNOTATION_CLASS_NAME})
public class InitialMarkProcessor extends BaseProcessor {

    private static final String PACKAGE_NAME = Constant.BASE_PACKAGE_NAME + ".preloader";

    private static final String ANNOTATION_PACKAGE_NAME = PACKAGE_NAME + ".annotation";

    public static final String ANNOTATION_CLASS_NAME = ANNOTATION_PACKAGE_NAME + ".InitialMark";

    private static final String TEMPLATE_PACKAGE_NAME = PACKAGE_NAME + ".template";

    private static final String INITIALIZER_CLASS_NAME = TEMPLATE_PACKAGE_NAME + ".IInitializer";
    private static final String PROJECT_NAME = "Preload";
    /**
     * Root 文件名
     */
    public static final String NAME_OF_ROOT = PROJECT_NAME + Constant.CLASS_NAME_DELIMITER + "Root" + Constant.CLASS_NAME_DELIMITER;
    private static final String NAME_OF_GROUP = PROJECT_NAME + Constant.CLASS_NAME_DELIMITER + "Group" + Constant.CLASS_NAME_DELIMITER;
    private static final String GROUP_CLASS_NAME = TEMPLATE_PACKAGE_NAME + ".IInitializerGroup";
    private static final String ROOT_CLASS_NAME = TEMPLATE_PACKAGE_NAME + ".IInitializerRoot";
    private static final String METHOD_LOAD_INTO = "loadInto";
    /**
     * 输出文件所在包
     */
    private static final String OUT_PUT_PACKAGE_NAME = PACKAGE_NAME + ".out";
    private Map<String, Element> mAnnotatedClassMap;
    private Map<String, String> mRootMap;
    private Map<String, Set<BeanMarkInfo>> mGroupMap;
    /**
     * IInitializer模板元素
     */
    private TypeMirror mInitializerType;
    /**
     * IInitializerGroup 类型
     */
    private TypeElement mGroupType;
    /**
     * Group的Map类型
     */
    private ParameterizedTypeName mGroupMapType;
    /**
     * Group参数 map
     */
    private ParameterSpec mGroupMapParam;
    /**
     * IInitializerRoot 类型
     */
    private TypeElement mRootType;
    private ParameterizedTypeName mRootMapType;
    /**
     * Root 参数 map
     */
    private ParameterSpec mRootMapParam;


    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mAnnotatedClassMap = new TreeMap<>();
        mRootMap = new TreeMap<>();
        mGroupMap = new HashMap<>(4);
        UtilFactory.getInstance().getLogUtil().setTag(PROJECT_NAME);
        UtilFactory.getInstance().getLogUtil().info("______PreloadProcessor is init______");
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        mAnnotatedClassMap.clear();
        mRootMap.clear();
        mGroupMap.clear();
        if (CollectionUtils.isEmpty(set)) {
            return false;
        }
        mAnnotatedClassMap.putAll(screenElement(roundEnvironment.getElementsAnnotatedWith(InitialMark.class), ElementKind.CLASS));
        try {
            this.parseAnnotated();
        } catch (Exception e) {
            e.printStackTrace();
            UtilFactory.getInstance().getLogUtil().error(e);
        }
        return 0 < mAnnotatedClassMap.size();
    }

    private void parseAnnotated() {
        if (MapUtils.isEmpty(mAnnotatedClassMap)) {
            return;
        }
        initType();
        initParam();
        for (Element element : mAnnotatedClassMap.values()) {
            inductionElement(element);
        }
        writeGroup();
        writeRoot();
        UtilFactory.getInstance().getLogUtil().info("______PreloadProcessor is completed______");
    }

    private void initType() {
        mInitializerType = UtilFactory.getInstance().getTypeUtils().generateType(INITIALIZER_CLASS_NAME);
        mGroupType = UtilFactory.getInstance().getElements().getTypeElement(GROUP_CLASS_NAME);
        mRootType = UtilFactory.getInstance().getElements().getTypeElement(ROOT_CLASS_NAME);
        mGroupMapType = ParameterizedTypeName.get(ClassName.get(Map.class), ClassName.get(String.class), ParameterizedTypeName.get(ClassName.get(Class.class), WildcardTypeName.subtypeOf(ClassName.get(mInitializerType))));
        mRootMapType = ParameterizedTypeName.get(ClassName.get(Map.class), ClassName.get(String.class), ParameterizedTypeName.get(ClassName.get(Class.class), WildcardTypeName.subtypeOf(ClassName.get(mGroupType))));
    }

    private void initParam() {
        mGroupMapParam = ParameterSpec.builder(mGroupMapType, Constant.NAME_OF_PARAM_FIELD_MAP).build();
        mRootMapParam = ParameterSpec.builder(mRootMapType, Constant.NAME_OF_PARAM_FIELD_MAP).build();
    }

    /**
     * 归纳元素
     */
    private void inductionElement(Element element) {
        TypeMirror marked = element.asType();
        if (!UtilFactory.getInstance().getTypes().isSubtype(marked, mInitializerType)) {
            UtilFactory.getInstance().getLogUtil().error("Preload in Compiler Found unsupported class type,type = [" + element.toString() + "].");
            return;
        }
        InitialMark mark = element.getAnnotation(InitialMark.class);
        if (StringUtils.isEmpty(mark.Path())) {
            UtilFactory.getInstance().getLogUtil().error("[" + element.toString() + "] When using [" + InitialMark.class.getName() + "], Path must be assigned a value");
            return;
        }
        String group = getGroup(mark.Group(), mark.Path());
        if (StringUtils.isEmpty(group)) {
            UtilFactory.getInstance().getLogUtil().error("[" + element.toString() + "] When using [" + InitialMark.class.getName() + "],Please assign value to group by path or group");
            return;
        }
        BeanMarkInfo info = new BeanMarkInfo();
        info.setGroup(group);
        info.setPath(mark.Path());
        info.setTypeMirror(marked);
        Set<BeanMarkInfo> elementSet = mGroupMap.get(group);
        UtilFactory.getInstance().getLogUtil().info("______elementSet:______" + elementSet);
        if (CollectionUtils.isNotEmpty(elementSet)) {
            elementSet.add(info);
            UtilFactory.getInstance().getLogUtil().info("______elementSet^Size:______" + elementSet.size());
            return;
        }
        elementSet = new TreeSet<>();
        elementSet.add(info);
        UtilFactory.getInstance().getLogUtil().info("______elementSet^Size:______" + elementSet.size());
        mGroupMap.put(group, elementSet);
    }

    private void writeGroup() {
        for (Map.Entry<String, Set<BeanMarkInfo>> entry : mGroupMap.entrySet()) {
            if (CollectionUtils.isEmpty(entry.getValue())) {
                continue;
            }
            String groupName = entry.getKey();
            String className = NAME_OF_GROUP + groupName;
            TypeSpec.Builder builder = generateGroupClass(className);
            builder.addMethod(generateGroupMethod(entry.getValue()));
            WriteJavaUtil.writeFactory(OUT_PUT_PACKAGE_NAME, className, builder.build());
            mRootMap.put(groupName, className);
        }
    }

    private void writeRoot() {
        String className = NAME_OF_ROOT + mModuleName;
        TypeSpec.Builder builder = generateRootClass(className);
        builder.addMethod(generateRootMethod());
        WriteJavaUtil.writeFactory(OUT_PUT_PACKAGE_NAME, className, builder.build());
    }

    private String getGroup(String group, String path) {
        if (StringUtils.isNotEmpty(group)) {
            return group;
        }
        if (!path.startsWith(Constant.PATH_DELIMITER)) {
            return null;
        }
        String defaultGroup = path.substring(1, path.indexOf(Constant.PATH_DELIMITER, 1));
        if (StringUtils.isEmpty(defaultGroup)) {
            return null;
        }
        return defaultGroup;
    }

    /**
     * 生成Group 类
     *
     * @param className 类名
     * @return {@link TypeSpec.Builder}
     */
    private TypeSpec.Builder generateGroupClass(String className) {
        return TypeSpec.classBuilder(className)
                .addSuperinterface(ClassName.get(mGroupType))
                .addModifiers(PUBLIC);
    }

    /**
     * 生成Group的loadInto函数
     *
     * @param set 元素
     * @return {@link MethodSpec}
     */
    private MethodSpec generateGroupMethod(Set<BeanMarkInfo> set) {
        MethodSpec.Builder builder = MethodSpec.methodBuilder(METHOD_LOAD_INTO)
                .addAnnotation(Override.class)
                .addModifiers(PUBLIC)
                .addParameter(mGroupMapParam);
        UtilFactory.getInstance().getLogUtil().info("______BeanMarkInfo size:______" + set.size());
        for (BeanMarkInfo info : set) {
            builder.addStatement("$N.put($S, $T.class)", Constant.NAME_OF_PARAM_FIELD_MAP, info.getPath(), ClassName.get(info.getTypeMirror()));
        }
        return builder.build();
    }

    private TypeSpec.Builder generateRootClass(String className) {
        return TypeSpec.classBuilder(className)
                .addSuperinterface(ClassName.get(mRootType))
                .addModifiers(PUBLIC);
    }

    private MethodSpec generateRootMethod() {
        MethodSpec.Builder builder = MethodSpec.methodBuilder(METHOD_LOAD_INTO)
                .addAnnotation(Override.class)
                .addModifiers(PUBLIC)
                .addParameter(mRootMapParam);
        if (MapUtils.isEmpty(mRootMap)) {
            return builder.build();
        }
        for (Map.Entry<String, String> entry : mRootMap.entrySet()) {
            builder.addStatement("$N.put($S, $T.class)", Constant.NAME_OF_PARAM_FIELD_MAP, entry.getKey(), ClassName.get(OUT_PUT_PACKAGE_NAME, entry.getValue()));
        }
        return builder.build();
    }


}
