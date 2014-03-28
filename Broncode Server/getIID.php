<?php

	$intresse = $_POST["intresse"];	

	$host = "localhost";
	$username = "carstpb26_plab";
	$password = "PixelBroS";
	$dbname = "carstpb26_plab";

	$con=mysqli_connect($host, $username, $password, $dbname);

	// Check connection
	if (mysqli_connect_error($con)){
		echo "Failed to connect to MySQL: " . mysqli_connect_error();
	}else{
		$result = mysqli_query($con,"SELECT id FROM Intresses WHERE categorie = '". $intresse ."'");

		$row = mysqli_fetch_row($result);
		echo "Id: ", $row[0];
		
		echo "query's succesvol afgerond";
	}
	//$result->free();
	mysqli_close($con);
?>
