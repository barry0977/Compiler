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
@a = global ptr null
define void @_init_() {
entry:
	%0 = call ptr @_malloc_array(i32 4)
	store ptr %0, ptr @a;
	ret void;
}

define i32 @main() {
entry:
	call void @_init_()
	%b.1.1 = alloca ptr;
	%0 = call ptr @_malloc_array(i32 4)
	store ptr %0, ptr %b.1.1;
	%1 = load ptr, ptr %b.1.1;
	%2 = getelementptr ptr, ptr %1, i32 2;
	%3 = load i32, ptr %2;
	store i32 2, ptr %2;
	%4 = load ptr, ptr @a;
	%5 = load ptr, ptr %b.1.1;
	store ptr %5, ptr @a;
	%6 = load ptr, ptr %b.1.1;
	%7 = getelementptr ptr, ptr %6, i32 2;
	%8 = load i32, ptr %7;
	%9 = call ptr @toString(i32 %8)
	call void @println(ptr %9)
	%10 = load ptr, ptr @a;
	%11 = getelementptr ptr, ptr %10, i32 2;
	%12 = load i32, ptr %11;
	%13 = call ptr @toString(i32 %12)
	call void @println(ptr %13)
	ret i32 0;
}

