<?php

	$id = $_GET["id"];

	$host = "localhost";
	$username = "carstpb26_plab";
	$password = "PixelBroS";
	$dbname = "carstpb26_plab";

	$minutes = 60 * 60 * 12;

	$con=mysqli_connect($host, $username, $password, $dbname);

	// Check connection
	if (mysqli_connect_errno($con)){
		echo "Failed to connect to MySQL: " . mysqli_connect_error();
	}else{
		
		mysqli_query($con,"CREATE OR REPLACE VIEW groupedUrlView AS SELECT url, COUNT(*) AS count FROM Users, UI, Intresses, IR, Reclames WHERE Users.id = UI.id_Users AND Intresses.id = UI.id_Intresses AND Intresses.id = IR.id_Intresses AND IR.id_Reclames = Reclames.id AND Users.id_Boards = ".$id." GROUP BY url") or die('Er ging iets mis 2'.mysqli_error($con));
		$result = mysqli_query($con,"SELECT url, count FROM groupedUrlView ORDER BY count DESC") or die('Er ging iets mis 3'.mysqli_error($con));
		
	}


	$return_arr = array();
	
	if($result){
		while($row = mysqli_fetch_array($result)){
	  		$row_array['url'] = $row['url'];

			array_push($return_arr,$row_array);
	  	}
	   	mysqli_free_result($result);
	}
	else{
   		die('Er ging iets mis 4'.mysqli_error($con)) ;
	}

	mysqli_close($con);

	echo json_encode($return_arr);
?>
