# CMAKE generated file: DO NOT EDIT!
# Generated by "Unix Makefiles" Generator, CMake Version 3.30

# Delete rule output on recipe failure.
.DELETE_ON_ERROR:

#=============================================================================
# Special targets provided by cmake.

# Disable implicit rules so canonical targets will work.
.SUFFIXES:

# Disable VCS-based implicit rules.
% : %,v

# Disable VCS-based implicit rules.
% : RCS/%

# Disable VCS-based implicit rules.
% : RCS/%,v

# Disable VCS-based implicit rules.
% : SCCS/s.%

# Disable VCS-based implicit rules.
% : s.%

.SUFFIXES: .hpux_make_needs_suffix_list

# Command-line flag to silence nested $(MAKE).
$(VERBOSE)MAKESILENT = -s

#Suppress display of executed commands.
$(VERBOSE).SILENT:

# A target that is always out of date.
cmake_force:
.PHONY : cmake_force

#=============================================================================
# Set environment variables for the build.

# The shell in which to execute make rules.
SHELL = /bin/sh

# The CMake executable.
CMAKE_COMMAND = /opt/homebrew/Cellar/cmake/3.30.3/bin/cmake

# The command to remove a file.
RM = /opt/homebrew/Cellar/cmake/3.30.3/bin/cmake -E rm -f

# Escaping for special characters.
EQUALS = =

# The top-level source directory on which CMake was run.
CMAKE_SOURCE_DIR = /Users/jjhuang/Project/CS6010_FinalProject

# The top-level build directory on which CMake was run.
CMAKE_BINARY_DIR = /Users/jjhuang/Project/CS6010_FinalProject/build

# Include any dependencies generated for this target.
include CMakeFiles/asteroids.dir/depend.make
# Include any dependencies generated by the compiler for this target.
include CMakeFiles/asteroids.dir/compiler_depend.make

# Include the progress variables for this target.
include CMakeFiles/asteroids.dir/progress.make

# Include the compile flags for this target's objects.
include CMakeFiles/asteroids.dir/flags.make

CMakeFiles/asteroids.dir/src/asteroid.cpp.o: CMakeFiles/asteroids.dir/flags.make
CMakeFiles/asteroids.dir/src/asteroid.cpp.o: /Users/jjhuang/Project/CS6010_FinalProject/src/asteroid.cpp
CMakeFiles/asteroids.dir/src/asteroid.cpp.o: CMakeFiles/asteroids.dir/compiler_depend.ts
	@$(CMAKE_COMMAND) -E cmake_echo_color "--switch=$(COLOR)" --green --progress-dir=/Users/jjhuang/Project/CS6010_FinalProject/build/CMakeFiles --progress-num=$(CMAKE_PROGRESS_1) "Building CXX object CMakeFiles/asteroids.dir/src/asteroid.cpp.o"
	/Applications/Xcode.app/Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -MD -MT CMakeFiles/asteroids.dir/src/asteroid.cpp.o -MF CMakeFiles/asteroids.dir/src/asteroid.cpp.o.d -o CMakeFiles/asteroids.dir/src/asteroid.cpp.o -c /Users/jjhuang/Project/CS6010_FinalProject/src/asteroid.cpp

CMakeFiles/asteroids.dir/src/asteroid.cpp.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color "--switch=$(COLOR)" --green "Preprocessing CXX source to CMakeFiles/asteroids.dir/src/asteroid.cpp.i"
	/Applications/Xcode.app/Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E /Users/jjhuang/Project/CS6010_FinalProject/src/asteroid.cpp > CMakeFiles/asteroids.dir/src/asteroid.cpp.i

CMakeFiles/asteroids.dir/src/asteroid.cpp.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color "--switch=$(COLOR)" --green "Compiling CXX source to assembly CMakeFiles/asteroids.dir/src/asteroid.cpp.s"
	/Applications/Xcode.app/Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -S /Users/jjhuang/Project/CS6010_FinalProject/src/asteroid.cpp -o CMakeFiles/asteroids.dir/src/asteroid.cpp.s

CMakeFiles/asteroids.dir/src/game.cpp.o: CMakeFiles/asteroids.dir/flags.make
CMakeFiles/asteroids.dir/src/game.cpp.o: /Users/jjhuang/Project/CS6010_FinalProject/src/game.cpp
CMakeFiles/asteroids.dir/src/game.cpp.o: CMakeFiles/asteroids.dir/compiler_depend.ts
	@$(CMAKE_COMMAND) -E cmake_echo_color "--switch=$(COLOR)" --green --progress-dir=/Users/jjhuang/Project/CS6010_FinalProject/build/CMakeFiles --progress-num=$(CMAKE_PROGRESS_2) "Building CXX object CMakeFiles/asteroids.dir/src/game.cpp.o"
	/Applications/Xcode.app/Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -MD -MT CMakeFiles/asteroids.dir/src/game.cpp.o -MF CMakeFiles/asteroids.dir/src/game.cpp.o.d -o CMakeFiles/asteroids.dir/src/game.cpp.o -c /Users/jjhuang/Project/CS6010_FinalProject/src/game.cpp

CMakeFiles/asteroids.dir/src/game.cpp.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color "--switch=$(COLOR)" --green "Preprocessing CXX source to CMakeFiles/asteroids.dir/src/game.cpp.i"
	/Applications/Xcode.app/Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E /Users/jjhuang/Project/CS6010_FinalProject/src/game.cpp > CMakeFiles/asteroids.dir/src/game.cpp.i

CMakeFiles/asteroids.dir/src/game.cpp.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color "--switch=$(COLOR)" --green "Compiling CXX source to assembly CMakeFiles/asteroids.dir/src/game.cpp.s"
	/Applications/Xcode.app/Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -S /Users/jjhuang/Project/CS6010_FinalProject/src/game.cpp -o CMakeFiles/asteroids.dir/src/game.cpp.s

CMakeFiles/asteroids.dir/src/laser.cpp.o: CMakeFiles/asteroids.dir/flags.make
CMakeFiles/asteroids.dir/src/laser.cpp.o: /Users/jjhuang/Project/CS6010_FinalProject/src/laser.cpp
CMakeFiles/asteroids.dir/src/laser.cpp.o: CMakeFiles/asteroids.dir/compiler_depend.ts
	@$(CMAKE_COMMAND) -E cmake_echo_color "--switch=$(COLOR)" --green --progress-dir=/Users/jjhuang/Project/CS6010_FinalProject/build/CMakeFiles --progress-num=$(CMAKE_PROGRESS_3) "Building CXX object CMakeFiles/asteroids.dir/src/laser.cpp.o"
	/Applications/Xcode.app/Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -MD -MT CMakeFiles/asteroids.dir/src/laser.cpp.o -MF CMakeFiles/asteroids.dir/src/laser.cpp.o.d -o CMakeFiles/asteroids.dir/src/laser.cpp.o -c /Users/jjhuang/Project/CS6010_FinalProject/src/laser.cpp

CMakeFiles/asteroids.dir/src/laser.cpp.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color "--switch=$(COLOR)" --green "Preprocessing CXX source to CMakeFiles/asteroids.dir/src/laser.cpp.i"
	/Applications/Xcode.app/Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E /Users/jjhuang/Project/CS6010_FinalProject/src/laser.cpp > CMakeFiles/asteroids.dir/src/laser.cpp.i

CMakeFiles/asteroids.dir/src/laser.cpp.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color "--switch=$(COLOR)" --green "Compiling CXX source to assembly CMakeFiles/asteroids.dir/src/laser.cpp.s"
	/Applications/Xcode.app/Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -S /Users/jjhuang/Project/CS6010_FinalProject/src/laser.cpp -o CMakeFiles/asteroids.dir/src/laser.cpp.s

CMakeFiles/asteroids.dir/src/main.cpp.o: CMakeFiles/asteroids.dir/flags.make
CMakeFiles/asteroids.dir/src/main.cpp.o: /Users/jjhuang/Project/CS6010_FinalProject/src/main.cpp
CMakeFiles/asteroids.dir/src/main.cpp.o: CMakeFiles/asteroids.dir/compiler_depend.ts
	@$(CMAKE_COMMAND) -E cmake_echo_color "--switch=$(COLOR)" --green --progress-dir=/Users/jjhuang/Project/CS6010_FinalProject/build/CMakeFiles --progress-num=$(CMAKE_PROGRESS_4) "Building CXX object CMakeFiles/asteroids.dir/src/main.cpp.o"
	/Applications/Xcode.app/Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -MD -MT CMakeFiles/asteroids.dir/src/main.cpp.o -MF CMakeFiles/asteroids.dir/src/main.cpp.o.d -o CMakeFiles/asteroids.dir/src/main.cpp.o -c /Users/jjhuang/Project/CS6010_FinalProject/src/main.cpp

CMakeFiles/asteroids.dir/src/main.cpp.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color "--switch=$(COLOR)" --green "Preprocessing CXX source to CMakeFiles/asteroids.dir/src/main.cpp.i"
	/Applications/Xcode.app/Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E /Users/jjhuang/Project/CS6010_FinalProject/src/main.cpp > CMakeFiles/asteroids.dir/src/main.cpp.i

CMakeFiles/asteroids.dir/src/main.cpp.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color "--switch=$(COLOR)" --green "Compiling CXX source to assembly CMakeFiles/asteroids.dir/src/main.cpp.s"
	/Applications/Xcode.app/Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -S /Users/jjhuang/Project/CS6010_FinalProject/src/main.cpp -o CMakeFiles/asteroids.dir/src/main.cpp.s

CMakeFiles/asteroids.dir/src/ship.cpp.o: CMakeFiles/asteroids.dir/flags.make
CMakeFiles/asteroids.dir/src/ship.cpp.o: /Users/jjhuang/Project/CS6010_FinalProject/src/ship.cpp
CMakeFiles/asteroids.dir/src/ship.cpp.o: CMakeFiles/asteroids.dir/compiler_depend.ts
	@$(CMAKE_COMMAND) -E cmake_echo_color "--switch=$(COLOR)" --green --progress-dir=/Users/jjhuang/Project/CS6010_FinalProject/build/CMakeFiles --progress-num=$(CMAKE_PROGRESS_5) "Building CXX object CMakeFiles/asteroids.dir/src/ship.cpp.o"
	/Applications/Xcode.app/Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -MD -MT CMakeFiles/asteroids.dir/src/ship.cpp.o -MF CMakeFiles/asteroids.dir/src/ship.cpp.o.d -o CMakeFiles/asteroids.dir/src/ship.cpp.o -c /Users/jjhuang/Project/CS6010_FinalProject/src/ship.cpp

CMakeFiles/asteroids.dir/src/ship.cpp.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color "--switch=$(COLOR)" --green "Preprocessing CXX source to CMakeFiles/asteroids.dir/src/ship.cpp.i"
	/Applications/Xcode.app/Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E /Users/jjhuang/Project/CS6010_FinalProject/src/ship.cpp > CMakeFiles/asteroids.dir/src/ship.cpp.i

CMakeFiles/asteroids.dir/src/ship.cpp.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color "--switch=$(COLOR)" --green "Compiling CXX source to assembly CMakeFiles/asteroids.dir/src/ship.cpp.s"
	/Applications/Xcode.app/Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -S /Users/jjhuang/Project/CS6010_FinalProject/src/ship.cpp -o CMakeFiles/asteroids.dir/src/ship.cpp.s

CMakeFiles/asteroids.dir/src/window.cpp.o: CMakeFiles/asteroids.dir/flags.make
CMakeFiles/asteroids.dir/src/window.cpp.o: /Users/jjhuang/Project/CS6010_FinalProject/src/window.cpp
CMakeFiles/asteroids.dir/src/window.cpp.o: CMakeFiles/asteroids.dir/compiler_depend.ts
	@$(CMAKE_COMMAND) -E cmake_echo_color "--switch=$(COLOR)" --green --progress-dir=/Users/jjhuang/Project/CS6010_FinalProject/build/CMakeFiles --progress-num=$(CMAKE_PROGRESS_6) "Building CXX object CMakeFiles/asteroids.dir/src/window.cpp.o"
	/Applications/Xcode.app/Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -MD -MT CMakeFiles/asteroids.dir/src/window.cpp.o -MF CMakeFiles/asteroids.dir/src/window.cpp.o.d -o CMakeFiles/asteroids.dir/src/window.cpp.o -c /Users/jjhuang/Project/CS6010_FinalProject/src/window.cpp

CMakeFiles/asteroids.dir/src/window.cpp.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color "--switch=$(COLOR)" --green "Preprocessing CXX source to CMakeFiles/asteroids.dir/src/window.cpp.i"
	/Applications/Xcode.app/Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E /Users/jjhuang/Project/CS6010_FinalProject/src/window.cpp > CMakeFiles/asteroids.dir/src/window.cpp.i

CMakeFiles/asteroids.dir/src/window.cpp.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color "--switch=$(COLOR)" --green "Compiling CXX source to assembly CMakeFiles/asteroids.dir/src/window.cpp.s"
	/Applications/Xcode.app/Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -S /Users/jjhuang/Project/CS6010_FinalProject/src/window.cpp -o CMakeFiles/asteroids.dir/src/window.cpp.s

# Object files for target asteroids
asteroids_OBJECTS = \
"CMakeFiles/asteroids.dir/src/asteroid.cpp.o" \
"CMakeFiles/asteroids.dir/src/game.cpp.o" \
"CMakeFiles/asteroids.dir/src/laser.cpp.o" \
"CMakeFiles/asteroids.dir/src/main.cpp.o" \
"CMakeFiles/asteroids.dir/src/ship.cpp.o" \
"CMakeFiles/asteroids.dir/src/window.cpp.o"

# External object files for target asteroids
asteroids_EXTERNAL_OBJECTS =

/Users/jjhuang/Project/CS6010_FinalProject/bin/asteroids: CMakeFiles/asteroids.dir/src/asteroid.cpp.o
/Users/jjhuang/Project/CS6010_FinalProject/bin/asteroids: CMakeFiles/asteroids.dir/src/game.cpp.o
/Users/jjhuang/Project/CS6010_FinalProject/bin/asteroids: CMakeFiles/asteroids.dir/src/laser.cpp.o
/Users/jjhuang/Project/CS6010_FinalProject/bin/asteroids: CMakeFiles/asteroids.dir/src/main.cpp.o
/Users/jjhuang/Project/CS6010_FinalProject/bin/asteroids: CMakeFiles/asteroids.dir/src/ship.cpp.o
/Users/jjhuang/Project/CS6010_FinalProject/bin/asteroids: CMakeFiles/asteroids.dir/src/window.cpp.o
/Users/jjhuang/Project/CS6010_FinalProject/bin/asteroids: CMakeFiles/asteroids.dir/build.make
/Users/jjhuang/Project/CS6010_FinalProject/bin/asteroids: /opt/homebrew/lib/libsfml-graphics.2.6.1.dylib
/Users/jjhuang/Project/CS6010_FinalProject/bin/asteroids: /opt/homebrew/lib/libsfml-system.2.6.1.dylib
/Users/jjhuang/Project/CS6010_FinalProject/bin/asteroids: /opt/homebrew/lib/libsfml-window.2.6.1.dylib
/Users/jjhuang/Project/CS6010_FinalProject/bin/asteroids: /opt/homebrew/lib/libsfml-system.2.6.1.dylib
/Users/jjhuang/Project/CS6010_FinalProject/bin/asteroids: CMakeFiles/asteroids.dir/link.txt
	@$(CMAKE_COMMAND) -E cmake_echo_color "--switch=$(COLOR)" --green --bold --progress-dir=/Users/jjhuang/Project/CS6010_FinalProject/build/CMakeFiles --progress-num=$(CMAKE_PROGRESS_7) "Linking CXX executable /Users/jjhuang/Project/CS6010_FinalProject/bin/asteroids"
	$(CMAKE_COMMAND) -E cmake_link_script CMakeFiles/asteroids.dir/link.txt --verbose=$(VERBOSE)

# Rule to build all files generated by this target.
CMakeFiles/asteroids.dir/build: /Users/jjhuang/Project/CS6010_FinalProject/bin/asteroids
.PHONY : CMakeFiles/asteroids.dir/build

CMakeFiles/asteroids.dir/clean:
	$(CMAKE_COMMAND) -P CMakeFiles/asteroids.dir/cmake_clean.cmake
.PHONY : CMakeFiles/asteroids.dir/clean

CMakeFiles/asteroids.dir/depend:
	cd /Users/jjhuang/Project/CS6010_FinalProject/build && $(CMAKE_COMMAND) -E cmake_depends "Unix Makefiles" /Users/jjhuang/Project/CS6010_FinalProject /Users/jjhuang/Project/CS6010_FinalProject /Users/jjhuang/Project/CS6010_FinalProject/build /Users/jjhuang/Project/CS6010_FinalProject/build /Users/jjhuang/Project/CS6010_FinalProject/build/CMakeFiles/asteroids.dir/DependInfo.cmake "--color=$(COLOR)"
.PHONY : CMakeFiles/asteroids.dir/depend
