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
%class.A = type {i32 }
define i32 @A.f(ptr %this) {
entry:
	%this.addr = alloca ptr;
	store ptr %this, ptr %this.addr;
	%this.this = load ptr, ptr %this.addr;
	%0 = getelementptr %class.A, ptr %this.this, i32 0, i32 0;
	%1 = load i32, ptr %0;
	ret i32 %1;
}

define ptr @A.copy(ptr %this) {
entry:
	%this.addr = alloca ptr;
	store ptr %this, ptr %this.addr;
	%this.this = load ptr, ptr %this.addr;
	ret ptr %this.this;
}

define i32 @main() {
entry:
	%hh.1.2 = alloca ptr;
	%0 = call ptr @_malloc(i32 1)
	store ptr %0, ptr %hh.1.2;
	%hhhh.1.2 = alloca ptr;
	%1 = load ptr, ptr %hh.1.2;
	%2 = call i32 @A.f(ptr %1)
	%3 = icmp slt i32 %2, 0;
	%hhhhhh.1.2 = alloca ptr;
	%ar.1.2 = alloca ptr;
	%br.1.2 = alloca ptr;
	br i1 %3, label %cond.true.0,label %cond.false.0;
cond.true.0:
	br label %cond.end.0;
cond.false.0:
	%4 = load ptr, ptr %hh.1.2;
	%5 = call ptr @A.copy(ptr %4)
	br label %cond.end.0;
cond.end.0:
	store ptr null, ptr %hhhh.1.2;
	%6 = load ptr, ptr %hhhh.1.2;
	%7 = call i32 @A.f(ptr %6)
	%8 = icmp sgt i32 %7, 0;
	br i1 %8, label %cond.true.1,label %cond.false.1;
cond.true.1:
	%9 = load ptr, ptr %hhhh.1.2;
	%10 = call ptr @A.copy(ptr %9)
	br label %cond.end.1;
cond.false.1:
	br label %cond.end.1;
cond.end.1:
	%11 = select i1 %8, ptr %10, ptr null
	store ptr %11, ptr %hhhhhh.1.2;
	%12 = call ptr @_malloc_array(i32 10)
	store ptr %12, ptr %ar.1.2;
	%13 = load ptr, ptr %ar.1.2;
	%14 = getelementptr ptr, ptr %13, i32 9;
	%15 = load i32, ptr %14;
	%16 = load ptr, ptr %ar.1.2;
	%17 = getelementptr ptr, ptr %16, i32 1;
	%18 = load i32, ptr %17;
	%19 = icmp sgt i32 %15, %18;
	br i1 %19, label %cond.true.2,label %cond.false.2;
cond.true.2:
	%20 = load ptr, ptr %ar.1.2;
	br label %cond.end.2;
cond.false.2:
	br label %cond.end.2;
cond.end.2:
	%21 = select i1 %19, ptr %20, ptr null
	store ptr %21, ptr %br.1.2;
	%22 = load ptr, ptr %hhhhhh.1.2;
	%23 = call ptr @A.copy(ptr %22)
	%24 = call ptr @A.copy(ptr %23)
	%25 = call ptr @A.copy(ptr %24)
	%26 = call ptr @A.copy(ptr %25)
	%27 = call i32 @A.f(ptr %26)
	%28 = load ptr, ptr %br.1.2;
	%29 = getelementptr ptr, ptr %28, i32 0;
	%30 = load i32, ptr %29;
	%31 = add i32 %27, %30
	ret i32 %31;
}

