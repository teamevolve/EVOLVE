version ?= 1.0.0
jar := runnables/evolve-$(version).jar
entrypoint ?= gui.GUI
OS ?= $(shell uname)

src_files := $(shell find src -type f -name '*.java')
pdf_files := $(shell find src -type f -name '*.pdf')
pdf_dest := $(patsubst src/%.pdf, build/classes/%.pdf, $(pdf_files))
rtf_files := $(shell find src -type f -name '*.rtf')
pdf_dest += $(patsubst src/%.rtf, build/classes/%.pdf, $(rtf_files))
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
	@mkdir -p $(@D)
	@jar cfe $(jar) $(entrypoint) -C build/classes .

run: build/recipts/src
	@mkdir -p output
	@java -cp build/classes:$(lib_cp) $(entrypoint) $(arg) $(sum)

build/recipts/src: $(src_files) $(pdf_dest)
	@mkdir -p $(@D)
	@mkdir -p build/classes
	@javac -d build/classes -cp $(lib_cp) $(src_files)
	@touch $@

build/classes/%.pdf: src/%.pdf
	@mkdir -p $(@D)
	@cp $< $@

build/classes/%.pdf: src/%.rtf
ifeq ($(shell which libreoffice), )
	@echo $< "could not be compiled; skipping"
else
	@mkdir -p $(@D)
	@libreoffice --headless --convert-to pdf --outdir $(@D) $< > /dev/null
endif

build/recipts/%: lib/%.jar
	@mkdir -p $(@D)
	@mkdir -p build/classes
	@unzip -d build/classes $< > /dev/null
	@rm -r build/classes/META-INF
	@touch $@
