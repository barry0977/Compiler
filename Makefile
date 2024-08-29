.PHONY: build
build:
	find -name '*.java' | xargs javac -d bin -cp /ulib/antlr-4.13.2-complete.jar

.PHONY: run
run:
	cd bin && java -cp /ulib/antlr-4.13.2-complete.jar:. Main

.PHONY: Sema
Sema:
	./testcases/sema/scripts/test.bash 'java -cp /ulib/antlr-4.13.2-complete.jar:bin Main' $(file)

.PHONY: Semall
Semall:
	time -p ./testcases/sema/scripts/test_all.bash 'java -cp /ulib/antlr-4.13.2-complete.jar:bin Main' testcases/sema/

.PHONY: IRa
IRa:
	./testcases/codegen/scripts/test_llvm_ir.bash 'java -cp /ulib/antlr-4.13.2-complete.jar:bin Main -emit-llvm' testcases/codegen/e1.mx src/IR/builtin/builtin.ll

.PHONY: IRall
IRall:
	./testcases/codegen/scripts/test_llvm_ir_all.bash 'java -cp /ulib/antlr-4.13.2-complete.jar:bin Main -emit-llvm' testcases/codegen/ src/IR/builtin/builtin.ll

.PHONY: ASMa
ASMa:
	./testcases/codegen/scripts/test_asm.bash 'java -cp /ulib/antlr-4.13.2-complete.jar:bin Main -S' testcases/codegen/e1.mx builtin.s

.PHONY: ASMall
ASMall:
	./testcases/codegen/scripts/test_all.bash 'java -cp /ulib/antlr-4.13.2-complete.jar:bin Main -S' testcases/codegen/ builtin.s