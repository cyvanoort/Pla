<?php

	$imei = $_POST["imei"];
	$id_Intresses = $_POST["id_Intresses"];
	$id_Boards = $_POST["id_Boards"];	

	$host = "localhost";
	$username = "carstpb26_plab";
	$password = "PixelBroS";
	$dbname = "carstpb26_plab";

	$con=mysqli_connect($host, $username, $password, $dbname);

	// Check connection
	if (mysqli_connect_errno($con)){
		echo "Failed to connect to MySQL: " . mysqli_connect_error();
	}else{
		mysqli_query($con,"INSERT INTO Users (imei, id_Boards) VALUES (".$imei.",".$id_Boards.")");
		mysqli_query($con,"INSERT INTO UI (id_Users, id_Intresses) VALUES ((SELECT MAX(id) FROM Users WHERE imei = ".$imei."), ".$id_Intresses.")");
		echo "query's succesvol afgerond";
		echo $imei." ".$intresse_id;
	}
	mysqli_close($con);
?>
