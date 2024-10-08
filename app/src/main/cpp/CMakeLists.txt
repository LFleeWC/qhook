# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.

cmake_minimum_required(VERSION 3.22.1)

# Declares and names the project.

project("status")

# Creates and names a library, sets it as either STATIC
# or SHARED, and provides the relative paths to its source code.
# You can define multiple libraries, and CMake builds them for you.
# Gradle automatically packages shared libraries with your APK.
include_directories(./android_util_api.h)


add_library( # Sets the name of the library.
        test
        # Sets the library as a shared library.
        SHARED
        # Provides a relative path to your source file(s).
        test.cpp
        elf_symbol_resolver.cpp
        miniz.c
        linker.cpp
        linker_soinfo.cpp
        linker_phdr.cpp
        linker_mapped_file_fragment.cpp
        linker_utils.cpp
        linker_debug.cpp
        linker_version.cpp
        linker_symbol.cpp
        linker_relocate.cpp
        jni_hook.cpp
        soinfo_12_transform.cpp
        soinfo_11_transform.cpp
        soinfo_121_transform.cpp
        android_util_api.cpp
  )


find_library( # Sets the name of the path variable.
        log-lib

        # Specifies the name of the NDK library that
        # you want CMake to locate.
        log)



target_link_libraries( # Specifies the target library.
        test
        # Links the target library to the log library
        # included in the NDK.
        ${log-lib} )

# Searches for a specified prebuilt library and stores the path as a
# variable. Because CMake includes system libraries in the search path by
# default, you only need to specify the name of the public NDK library
# you want to add. CMake verifies that the library exists before
# completing its build.

# Specifies libraries CMake should link to your target library. You
# can link multiple libraries, such as libraries you define in this
# build script, prebuilt third-party libraries, or system libraries.

