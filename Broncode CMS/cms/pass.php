<?php include '../include/check.php';?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta name="keywords" content="" />
<meta name="description" content="" />
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>Wachtwoord wijzigen</title>
<link href='http://fonts.googleapis.com/css?family=Oswald:400,300' rel='stylesheet' type='text/css' />
<link href='http://fonts.googleapis.com/css?family=Abel|Satisfy' rel='stylesheet' type='text/css' />
<link href="/include/default.css" rel="stylesheet" type="text/css" media="all" />
<script>
function checkform(){
	var a = document.forms["pass"]["user"].value;
	var b = document.forms["pass"]["pass1"].value;
	var c = document.forms["pass"]["pass2"].value;
	var d = document.forms["pass"]["pass3"].value;
	if (a == null || a == "") {
		alert("Je hebt je geen user ingevuld.");
		return false;
	}
	if (b == null || b == "") {
		alert("Je hebt geen oud wachtwoord ingevuld.");
		return false;
	}
	if (c == null || c == "") {
		alert("Je hebt geen nieuw wachtwoord ingevuld.");
		return false;
	}
	if (d == null || d == "") {
		alert("Je hebt het nieuwe wachtwoord niet nogmaals ingevuld.");
		return false;
	}
	if (c == d) {
		alert("Je hebt nieuwe wachtwoord komt niet overeen met controleveld.");
		return false;
	}

}
</script>
</head>
<body>
<?php include '../include/menu.php'; ?>
<div id="wrapper">
	<div id="page-wrapper">
		<div id="page">
			<div id="wide-content">
				<div>
					<h2>Wachtwoord wijzigen</h2>
					<p>
						<form method="POST" action="../include/wpas.php" onsubmit="checkform()">
						<table>
<tr>
<td>
<label>Gebruikersnaam</label>
</td>
<td>
<input type="text" name="user" />
</td>
</tr>
<tr>
<td>
<label>Oud wachtwoord</label>
</td>
<td>
<input type="password" name="pass1" />
</td>
</tr>
<tr>
<td>
<label>Nieuw wachtwoord</label>
</td>
<td>
<input type="password" name="pass2" />
</td>
</tr>
<tr>
<td>
<label>Controle wachtwoord</label>
</td>
<td>
<input type="password" name="pass3" />
</td>
</tr>
<tr>
<td>
<div  onclick="formObject.submit();">
							<button class="button-style">
								Toevoegen
							</button>
						</div>
</td>
</tr>
</table>
						
					</form>
					</p>
				</div>
			</div>
		</div>
	</div>
<div id="footer" class="container">
	<p>Copyright (c) 2013 Duterm Solutions All rights reserved.</p>
</div>
</body>
</html>
