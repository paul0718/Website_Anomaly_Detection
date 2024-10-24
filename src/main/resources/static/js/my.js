function validateLoginValues() {
	const userName = document.getElementById("loginUserName").value;
	const password = document.getElementById("loginPassword").value;
	if (!userName || userName.length < 5) {
		alert("Invalid user name, at least 5 chars");
		return;
	}
	if (!password || password.length < 5) {
		alert("Invalid password, at least 5 chars");
		return;
	}
	//TODO 如何验证必须有大小写字母,数字,特殊字符?
	
	document.getElementById("loginForm").submit();
}