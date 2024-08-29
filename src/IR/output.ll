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
@.str.0 = private unnamed_addr constant [4 x i8] c"aaa\00"
@.str.1 = private unnamed_addr constant [6 x i8] c"bbbbb\00"
define i32 @main() {
entry:
	%s1.1.1 = alloca ptr;
	store ptr @.str.0, ptr %s1.1.1;
	%s2.1.1 = alloca ptr;
	store ptr @.str.1, ptr %s2.1.1;
	%s3.1.1 = alloca ptr;
	%0 = load ptr, ptr %s1.1.1;
	%1 = load ptr, ptr %s2.1.1;
	%2 = call ptr @string.add(ptr %0, ptr %1)
	store ptr %2, ptr %s3.1.1;
	%3 = load ptr, ptr %s3.1.1;
	%4 = call i32 @string.length(ptr %3)
	%5 = load ptr, ptr %s3.1.1;
	%6 = call i32 @string.ord(ptr %5, i32 5)
	%7 = add i32 %4, %6
	ret i32 %7;
}

