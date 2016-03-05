<?php
include 'statics.php';
/*
  This is the class in which the functions which are used for communication server
  are collected.


*/

/*
   name : checkMessageTable
   function : check the table which has the messages for sending other client.
              & return the message for sending.
   parameters
    - $table_name : the table name which is searched for checking message.

   return : $message['tag']['content']
*/
function checkMessageTable($table_name){

  global $HOST, $DB_USER, $DB_PASSWORD, $DB_NAME, $MATCHING_TABLE, $MESSAGE_TABLE1, $MESSAGE_TABLE6, $MESSAGE_TABLE8;

    //connect to DB and table.
    $conn=mysqli_connect($HOST, $DB_USER, $DB_PASSWORD);
    mysqli_select_db($conn, $DB_NAME);
    $table_result = mysqli_query($conn, "SELECT * FROM ".$table_name);


    //make message
    if($row=mysqli_fetch_assoc($table_result)){

      $message['flag']=$row['flag'];
      //echo "flag : ".$message['flag'];
      for($i=0; $i<$row['num_contents']; $i++){
        $tag="tag_".($i+1);
        $content="content_".($i+1);
        $message[$row[$tag]]=$row[$content];
        //echo "/".$row[$tag].":".$message[$row[$tag]];
      }
    }else{

      if($table_name==$MESSAGE_TABLE6){
        $message['flag']=(string)"600";
      }else if($table_name==$MESSAGE_TABLE8){
        $message['flag']=(string)"800";
      }else if($table_name==$MESSAGE_TABLE1){
        $message['flag']=(string)"10";
      }
    }

    //delete message from the table.
    $sql="DELETE FROM ".$table_name." WHERE id=".$row['id'];
    if(mysqli_query($conn, $sql)){
      //query success!
    }else{
      //query failed!
    }
    //return the message.
    return $message;
}

/*
   name : storeTable
   function : store the datas(flag, num_contents, tags, contents) into the table.

   parameters
    - $table_name : the table name in which the datas will be stored.
    - $flag : the flag data.
    - $num_contents : the num_contents data.
                      This is used for check how many contents is in content array.
    - $content['int'] : the data array which will be stored into the database table in the tag & content colums.
              'int' : 0, 2, 4, ... : tag name
                    : 1, 3, 5, ... : content for each tag
              example : $content[0]="vehicle_code"
                        $content[1]="123456"
                        -> message ('flag','num_content','vehicle_code') value(100, 1, 123456)

   return : ( true / false ) store query result
*/
function storeMessageTable($table_name, $flag, $num_contents, $content){

  global $HOST, $DB_USER, $DB_PASSWORD, $DB_NAME, $MATCHING_TABLE, $MESSAGE_TABLE1, $MESSAGE_TABLE6, $MESSAGE_TABLE8;

  $conn=mysqli_connect($HOST, $DB_USER, $DB_PASSWORD);
  mysqli_select_db($conn, $DB_NAME);

  $sql="INSERT INTO ".$table_name." (flag,num_contents";
  $sql_tail="VALUES('".$flag."', ".$num_contents;

  for($i=0; $i<$num_contents; $i++){
    $sql=$sql.",tag_".($i+1).",content_".($i+1);
    $tmp_content1=$content[(2*$i)];
    $tmp_content2=$content[(2*$i)+1];
    $tmp_sql=", '".$tmp_content1."', '".$tmp_content2."'";
    $sql_tail=$sql_tail.$tmp_sql;
  }
  $sql=$sql.") ";
  $sql_tail=$sql_tail.")";

  $sql=$sql.$sql_tail;
  if(mysqli_query($conn, $sql)){
    return true;
  }else{
    return false;
  }

}


 ?>
