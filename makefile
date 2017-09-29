version ?= 1.0.0
jar := build/evolve-$(version).jar
entrypoint ?= gui.GUI

src_files := $(shell find src -type f -name '*.java')
lib_files := lib/jfreechart-1.0.19.jar lib/orsoncharts-1.5.jar lib/jcommon-1.0.23.jar # $(shell find lib -type f -name '*.jar')
lib_cp := $(shell echo $(lib_files) | sed "s/ /:/g")
lib_rcts := $(patsubst lib/%.jar, build/recipts/%, $(lib_files))

.PHONY: all clean run jar run-jar

all: build/recipts/src

clean:
	@rm -r build

run-jar: $(jar)
	@java -jar $(jar)

jar: $(jar)

$(jar): build/recipts/src $(lib_rcts)
	@jar cfe $(jar) $(entrypoint) -C build/classes .

run: build/recipts/src
	@java -cp build/classes:$(lib_cp) $(entrypoint) $(args)

build/recipts/src: $(src_files)
	@mkdir -p $(@D)
	@mkdir -p build/classes
	@javac -d build/classes -cp $(lib_cp) $(src_files)
	@touch $@

build/recipts/%: lib/%.jar
	@mkdir -p $(@D)
	@mkdir -p build/classes
	@unzip -d build/classes $< > /dev/null
	@rm -r build/classes/META-INF
	@touch $@
