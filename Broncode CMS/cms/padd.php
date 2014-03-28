<?php include '../include/check.php';?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta name="keywords" content="" />
<meta name="description" content="" />
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>PLAB toevoegen</title>
<link href='http://fonts.googleapis.com/css?family=Oswald:400,300' rel='stylesheet' type='text/css' />
<link href='http://fonts.googleapis.com/css?family=Abel|Satisfy' rel='stylesheet' type='text/css' />
<link href="/include/default.css" rel="stylesheet" type="text/css" media="all" />
<script>
function checkform(){
	var a = document.forms["padd"]["land"].value;
	var b = document.forms["padd"]["plaats"].value;
	var c = document.forms["padd"]["straat"].value;
	var d = document.forms["padd"]["la"].value;
	var e = document.forms["padd"]["lo"].value;
	if (d == null || d == "") {
		alert("Je hebt de lattitude nog niet ingevuld.");
		return false;
	}
	if (e == null || e == "") {
		alert("Je hebt de longtitude nog niet ingevuld.");
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
					<h2>PLAB toevoegen</h2>
					<p>
						<form name="padd" method="POST" onSubmit="return checkform()" action="../include/plabadd.php">
							<table>
								<tr>
									<td>
										<label>
											Breedtegraad
										</label>
									</td>
									<td>
										<input type="number" name="la" />
									</td>
								</tr>
								<tr>
									<td>
										<label>
											lengtegraad
										</label>
									</td>
								<td>
									<input type="number" name="lo" />
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
