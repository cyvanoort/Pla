<?php
	session_start();
require 'connect.php';
$user=$_POST['user'];
$pass=$_POST['pass'];

$result = mysqli_query($link, 'SELECT * FROM Login WHERE Datum = (SELECT MAX(Datum) FROM Login)') or die('Er ging iets mis11111' . mysql_error($link));

$row1 = mysqli_fetch_array($result);
$dbu=$row1["User"];
$dbp=$row1["Pass"];

if ($user==$dbu && $pass==$dbp){  
    $_SESSION['log']='1';
	echo '<meta http-equiv="refresh" content="1; URL=http://plab.carstenoort-pt.com/login_t.php">';
}
else{
	$_SESSION['log']='0';
	echo '<meta http-equiv="refresh" content="1; URL=http://plab.carstenoort-pt.com/index1.php">';	
}


?>