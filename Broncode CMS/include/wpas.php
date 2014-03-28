<?php
include 'connect.php';
$user = $_POST['user'];
$old = $_POST['pass1'];
$nieuw = $_POST['pass2'];
$nieuw_her = $_POST['pass3'];

$result = mysqli_query($link, 'SELECT * FROM Login WHERE Datum = (SELECT MAX(Datum) FROM Login)') or die('er ging iets mis2' . mysqli_error($link));
$row1 = mysqli_fetch_array($result);
$dbu=$row1["User"];
$dbp=$row1["Pass"];

if ($user==$dbu && $old==$dbp){
	if($nieuw == $nieuw_her){
		$change = mysqli_query($link, 'INSERT INTO Login (User, Pass) VALUES ("'. $user .'","'. $nieuw .'")') or die('er ging iets mis 2' . mysqli_error($link));
	}
	else{
	echo "Wachtwoord en controlewachtwoord zijn niet aan elkaar gelijk, probeer opnieuw.";
	echo '<meta http-equiv="refresh" content="5; URL=http://plab.carstenoort-pt.com/cms/pass.php">';
	}
}
else{
	echo "Verkeerd wachtwoord opgegeven, u wordt uitgelogd.";
	echo '<meta http-equiv="refresh" content="5; URL=http://plab.carstenoort-pt.com/cms/logout.php">';
}


?>