<?php
$allowedExts = array("gif", "jpeg", "jpg", "png");
$extension = end(explode(".", $_FILES["file"]["name"]));
if ((($_FILES["file"]["type"] == "image/gif")
|| ($_FILES["file"]["type"] == "image/jpeg")
|| ($_FILES["file"]["type"] == "image/jpg")
|| ($_FILES["file"]["type"] == "image/pjpeg")
|| ($_FILES["file"]["type"] == "image/x-png")
|| ($_FILES["file"]["type"] == "image/png"))
&& in_array($extension, $allowedExts))
  {
  if ($_FILES["file"]["error"] > 0)
    {
    echo "Return Code: " . $_FILES["file"]["error"] . "<br>";
    }
  else
    {
    echo "Upload: " . $_FILES["file"]["name"] . "<br>";
    echo "Temp file: " . $_FILES["file"]["tmp_name"] . "<br>";

    if (file_exists("../reclame/" . $_FILES["file"]["name"]))
      {
      echo $_FILES["file"]["name"] . " already exists. ";
      }
    else
      {
      move_uploaded_file($_FILES["file"]["tmp_name"],
      "../reclame/" . $_FILES["file"]["name"]);
      echo "Stored in: " . "../reclame/" . $_FILES["file"]["name"];
	  echo '<br />succesvol toegevoegd<br />';
	  echo '<meta http-equiv="refresh" content="5; URL=http://plab.carstenoort-pt.com/cms/radd.php">';
			include 'connect.php';
			$source = $_FILES["file"]["name"];
			$query = mysqli_query($link, 'INSERT INTO Reclames (url) VALUES ("'.$_FILES["file"]["name"].'")') or die("er ging iets mis 2" . mysqli_error($link));
			$query2 = mysqli_query($link, 'SELECT id FROM Reclames WHERE url="'.$_FILES["file"]["name"].'"') or die("er ging iets mis 3" . mysqli_error($link));
			$rid = mysqli_fetch_array($query2);
			$iid = $_POST['cat'];
			$query3 = mysqli_query($link, 'INSERT INTO IR (id_Intresses, id_Reclames) VALUES ("'.$iid.'","'.$rid[id].'")') or die('er ging iets mis 4'.mysqli_error($link));
      }
    }
  }
else
  {
  echo "Invalid file";
  }
?>