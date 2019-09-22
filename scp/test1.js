alert("这是简单的手动注入，接下来，网页标题将会改变!");
document.getElementsByTagName("title")[0].innerText = '标题被修改了'+parseInt(Math.random()*99999%1000);