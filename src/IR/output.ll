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
define i32 @main() {
entry:
	%a.1.1 = alloca i32;
	%0 = load i32, ptr %a.1.1;
	store i32 1, ptr %a.1.1;
	br label %for.cond.0;
for.cond.0:
	%1 = load i32, ptr %a.1.1;
	%2 = icmp sle i32 %1, 1000000;
	br i1 %2, label %for.body.0,label %for.end.0;
for.body.0:
	%3 = icmp ne i1 1, 0;
	br i1 %3, label %logic.rhs.1,label %logic.end.1;
logic.rhs.1:
	%4 = icmp ne i1 1, 0;
	br label %logic.end.1;
logic.end.1:
	%5 = select i1 %3, i1 %4, i1 0
	br i1 %5, label %if.then.2,label %if.else.2;
if.then.2:
	br label %if.end.2;
if.else.2:
	br label %if.end.2;
if.end.2:
	br label %for.step.0;
for.step.0:
	%6 = load i32, ptr %a.1.1;
	%7 = load i32, ptr %a.1.1;
	%8 = add i32 %7, 1
	store i32 %8, ptr %a.1.1;
	br label %for.cond.0;
for.end.0:
	ret i32 0;
}

