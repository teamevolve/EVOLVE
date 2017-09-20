version ?= 1.0.0
jar := build/evolve-$(version).jar
entrypoint ?= gui.GUI

src_files := $(shell find src -type f -name '*.java')
lib_files := $(shell find lib -type f -name '*.jar' | sed ":a;N;s/\n/:/;ba")

.PHONY: all clean run

all: $(jar)

clean:
	@rm -r build

run: $(jar)
	@java -jar $(jar)

$(jar): $(src_files)
	@mkdir -p build/classes
	@javac -d build/classes -cp $(lib_files) $(src_files)
	@jar cfe $(jar) $(entrypoint) -C build/classes .
