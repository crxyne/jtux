#!/bin/bash
g++ -I "/usr/lib/jvm/java-17-openjdk-amd64/include" -I "/usr/lib/jvm/java-17-openjdk-amd64/include/linux" JTuxLibrary.cpp -fPIC -m64 -shared -o jtux.so -lncurses