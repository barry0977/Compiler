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
@make = global ptr null
@count = global ptr null
define void @_init_() {
entry:
	%0 = call ptr @_malloc_array(i32 1)
	store ptr %0, ptr @count;
	ret void;
}

define void @origin(i32 %N) {
entry:
	%N.1.1 = alloca i32;
	store i32 %N, ptr %N.1.1;
	%0 = load ptr, ptr @make;
	%1 = load i32, ptr %N.1.1;
	%2 = call ptr @_malloc_array(i32 %1)
	store ptr %2, ptr @make;
	ret void;
}

define void @search() {
entry:
	ret void;
}

define i32 @main() {
entry:
	call void @_init_()
	call void @search()
	ret i32 0;
}

