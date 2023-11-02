javac -h ../src/main/java/org/crayne/sketch/util/lib ../src/main/java/org/crayne/sketch/util/lib/NativeSketchLibrary.java
mv ../src/main/java/org/crayne/sketch/util/lib/org_crayne_sketch_util_lib_NativeSketchLibrary.h .
rm ../src/main/java/org/crayne/sketch/util/lib/NativeSketchLibrary.class
rm SketchLibrary.h
mv org_crayne_sketch_util_lib_NativeSketchLibrary.h SketchLibrary.h