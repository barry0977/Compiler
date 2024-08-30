declare void @print(ptr)
declare void @println(ptr)
declare void @printInt(i32)
declare void @printlnInt(i32)
declare ptr @getString()
declare i32 @getInt()
declare ptr @toString(i32)
declare i32 @string.length(ptr)
declare ptr @string.substring(ptr, i32, i32)
declare i32 @string.parseInt(ptr)
declare i32 @string.ord(ptr, i32)
declare ptr @string.add(ptr, ptr)
declare i1 @string.equal(ptr, ptr)
declare i1 @string.notEqual(ptr, ptr)
declare i1 @string.less(ptr, ptr)
declare i1 @string.lessOrEqual(ptr, ptr)
declare i1 @string.greater(ptr, ptr)
declare i1 @string.greaterOrEqual(ptr, ptr)
declare i32 @array.size(ptr)
declare ptr @_malloc(i32)
declare ptr @_malloc_array(i32)
define void @test() {
entry:
	%a.1.1 = alloca i32;
	%0 = call i32 @getInt()
	store i32 %0, ptr %a.1.1;
	%1 = load i32, ptr %a.1.1;
	%2 = sdiv i32 %1, 2
	call void @printlnInt(i32 %2)
	ret void;
}

define i32 @main() {
entry:
	call void @test()
	call void @test()
	ret i32 0;
}

