<?php
    require_once 'oss_php_sdk_20140625/sdk.class.php';
    $id = '2onpuorvhikxergnrzmwkn0t';
    $key = '/mHJla5wI0zCPvu7xtP3gpWPvqs=';
    $host = 'http://shinenuaa.oss-test.aliyun-inc.com';
    $oss_sdk_service = new alioss($id, $key, $host);
    $dir = 'user-dir/';

    //最大文件大小.用户可以自己设置
    $condition = array(0=>'content-length-range', 1=>0, 2=>1048576000);
    $conditions[] = $condition; 

    //表示用户上传的数据,必须是以$dir开始, 不然上传会失败,这一步不是必须项,只是为了安全起见,防止用户通过policy上传到别人的目录
    $start = array(0=>'starts-with', 1=>'$key', 2=>$dir);
    $conditions[] = $start; 


    //这里默认设置是２０２０年.注意了,可以根据自己的逻辑,设定expire 时间.达到让前端定时到后面取signature的逻辑
    $arr = array('expiration'=>'2020-01-01T12:00:00.000Z','conditions'=>$conditions);
    $policy = json_encode($arr);
    $base64_policy = base64_encode($policy);
    $string_to_sign = $base64_policy;
    $signature = base64_encode(hash_hmac('sha1', $string_to_sign, $key, true));

    $response = array();
    $response['accessid'] = $id;
    $response['host'] = $host;
    $response['policy'] = $base64_policy;
    $response['signature'] = $signature;
    //这个参数是设置用户上传指定的前缀
    $response['dir'] = $dir;
    echo json_encode($response);
?>
