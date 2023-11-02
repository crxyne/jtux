#!/bin/bash
javac -h ../java/org/crayne/jtux/util/lib ../src/main/java/org/crayne/jtux/util/lib/NativeJTuxLibrary.java
mv ../src/main/java/org/crayne/jtux/util/lib/org_crayne_jtux_util_lib_NativeJTuxLibrary.h .
rm ../src/main/java/org/crayne/jtux/util/lib/NativeJTuxLibrary.class
rm JTuxLibrary.h
mv org_crayne_jtux_util_lib_NativeJTuxLibrary.h JTuxLibrary.h