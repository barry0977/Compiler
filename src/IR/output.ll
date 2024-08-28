declare void @print(ptr, ptr, i32, i32)
declare void @println()
declare void @printInt()
declare void @printlnInt()
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
%class.Queue_int = type {ptr, i32, i32 }
@.str.0 = private unnamed_addr constant [14 x i8] c"Passed tests.\00"
define void @Queue_int.push(ptr %this, i32 %v) {
entry:
	%this.addr = alloca ptr;
	store ptr %this, ptr %this.addr;
	%this.this = load ptr, ptr %this.addr;
	%v.2.1 = alloca i32;
	store i32 %v, ptr %v.2.1;
	%0 = getelementptr %class.Queue_int, ptr %this.this, i32 0, i32 0;
	%1 = load ptr, ptr %0;
	%2 = getelementptr %class.Queue_int, ptr %this.this, i32 0, i32 2;
	%3 = load i32, ptr %2;
	%4 = getelementptr ptr, ptr %1, i32 %3;
	%5 = load i32, ptr %4;
	%6 = load i32, ptr %v.2.1;
	store i32 %6, ptr %4;
	%7 = getelementptr %class.Queue_int, ptr %this.this, i32 0, i32 2;
	%8 = load i32, ptr %7;
	%9 = getelementptr %class.Queue_int, ptr %this.this, i32 0, i32 2;
	%10 = load i32, ptr %9;
	%11 = add i32 %10, 1
	%12 = getelementptr %class.Queue_int, ptr %this.this, i32 0, i32 0;
	%13 = load ptr, ptr %12;
	%14 = call i32 @array.size(ptr %13)
	%15 = srem i32 %11, %14
	store i32 %15, ptr %7;
	ret void;
}

define i32 @Queue_int.size(ptr %this) {
entry:
	%this.addr = alloca ptr;
	store ptr %this, ptr %this.addr;
	%this.this = load ptr, ptr %this.addr;
	%0 = getelementptr %class.Queue_int, ptr %this.this, i32 0, i32 2;
	%1 = load i32, ptr %0;
	%2 = getelementptr %class.Queue_int, ptr %this.this, i32 0, i32 0;
	%3 = load ptr, ptr %2;
	%4 = call i32 @array.size(ptr %3)
	%5 = add i32 %1, %4
	%6 = getelementptr %class.Queue_int, ptr %this.this, i32 0, i32 1;
	%7 = load i32, ptr %6;
	%8 = sub i32 %5, %7
	%9 = getelementptr %class.Queue_int, ptr %this.this, i32 0, i32 0;
	%10 = load ptr, ptr %9;
	%11 = call i32 @array.size(ptr %10)
	%12 = srem i32 %8, %11
	ret i32 %12;
}

define void @Queue_int.Queue_int(ptr %this) {
entry:
	%this.addr = alloca ptr;
	store ptr %this, ptr %this.addr;
	%this.this = load ptr, ptr %this.addr;
	%0 = getelementptr %class.Queue_int, ptr %this.this, i32 0, i32 1;
	%1 = load i32, ptr %0;
	store i32 0, ptr %0;
	%2 = getelementptr %class.Queue_int, ptr %this.this, i32 0, i32 2;
	%3 = load i32, ptr %2;
	store i32 0, ptr %2;
	%4 = getelementptr %class.Queue_int, ptr %this.this, i32 0, i32 0;
	%5 = load ptr, ptr %4;
	%6 = call ptr @_malloc_array(i32 16)
	store ptr %6, ptr %4;
	ret void;
}

define i32 @main() {
entry:
	%q.1.2 = alloca ptr;
	%0 = call ptr @_malloc(i32 3)
	call void @Queue_int.Queue_int(ptr %0)
	store ptr %0, ptr %q.1.2;
	%1 = load ptr, ptr %q.1.2;
	call void @Queue_int.push(ptr %1, i32 3)
	call void @println(ptr @.str.0)
	ret i32 0;
}

