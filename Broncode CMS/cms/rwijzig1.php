<?php include '../include/check.php';?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta name="keywords" content="" />
<meta name="description" content="" />
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>Reclame verwijderen</title>
<link href='http://fonts.googleapis.com/css?family=Oswald:400,300' rel='stylesheet' type='text/css' />
<link href='http://fonts.googleapis.com/css?family=Abel|Satisfy' rel='stylesheet' type='text/css' />
<link href="/include/default.css" rel="stylesheet" type="text/css" media="all" />
<script>
function pr(){
alert('verwijderd');
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
					<h2>Reclame verwijderen</h2>
					<p>
						<form method="post" action="../include/rdel.php">
<?php
$ad="+";
include '../include/connect.php';
$res = mysqli_query($link, "SELECT * FROM Reclames") or die("er ging iets mis 1" . mysqli_error($link));
echo '<select name="rec">';
while($row = mysqli_fetch_array($res)){
echo "<option value='".$row['id'].$ad.$row['url']."'>". $row['url']."</option>";
}
echo "</select>";
?>
<br />
<div  onclick="formObject.submit();">
							<button class="button-style">
								Verwijderen
							</button>
						</div>						
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
