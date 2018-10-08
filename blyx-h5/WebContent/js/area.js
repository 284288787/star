
function addS(){
    
    
    var slider = mui('#slider').slider();
    var selectNav = document.getElementById('selectNav');
    var selectNavLi = selectNav.getElementsByTagName('li');
    var oneUl = document.getElementById('select-con-1');
    var twoUl = document.getElementById('select-con-2');
    var threeUl = document.getElementById('select-con-3');
    var item1 = document.getElementById('item1');
    var item2 = document.getElementById('item2');
    var item3 = document.getElementById('item3');
    var index1 , index2 , index3;
    for( i=0 ; i<cityData3.length ; i++){
        var li = document.createElement("li");
        li.setAttribute('value',cityData3[i].value);
        li.innerHTML = cityData3[i].text;
        oneUl.appendChild(li)
    }
    
    var OneLi = oneUl.getElementsByTagName('li');
    for( i=0 ; i<OneLi.length ; i++){
        var a = OneLi[i];
        a.index = i;
        a.addEventListener('tap',function(){
            //console.log(OneLi[i])
            //清空第二个ul
            twoUl.innerHTML = '';
            selectNavLi[1].innerText = '请选择';
            selectNavLi[2].classList.add('mui-hidden');
            //获取下标和切换显示
            index1 = this.index;
            for(var i = 0; i < OneLi.length ; i++){
                OneLi[i].classList.remove('active');
            }
            this.classList.add('active');
            selectNavLi[0].innerText = this.innerText;
            selectNavLi[0].classList.add('active');
            selectNavLi[1].classList.remove('mui-hidden');
            //创建li并向第二个ul赋值
            var children = cityData3[index1].children;
            for( i=0 ; i<children.length ; i++){
                var li = document.createElement("li");
                li.setAttribute('value',children[i].value);
                li.innerHTML = children[i].text;
                twoUl.appendChild(li);
            }
            //显示第二个
            item2.classList.remove('mui-hidden');
            selectNavLi[1].classList.remove('active');
            slider.gotoItem(1,300);
            slider.stopped = false; //开启滑动切换
            
            var twoLi = twoUl.getElementsByTagName('li');
            for( i=0 ; i<twoLi.length ; i++){
                var a = twoLi[i];
                a.index = i;
                a.addEventListener('tap',function(){
                    //清空第二个ul
                    threeUl.innerHTML = '';
                    //获取下标和切换显示
                    index2 = this.index;
                    for(var i = 0; i < twoLi.length ; i++){
                        twoLi[i].classList.remove('active');
                    }
                    this.classList.add('active');
                    selectNavLi[1].innerText = this.innerText;
                    selectNavLi[1].classList.add('active');
                    selectNavLi[2].classList.remove('mui-hidden');
                    //创建li并向第二个ul赋值
                    var children = cityData3[index1].children[index2].children;
                    for( i=0 ; i<children.length ; i++){
                        var li = document.createElement("li");
                        li.setAttribute('value',children[i].value);
                        li.innerHTML = children[i].text;
                        threeUl.appendChild(li);
                    }
                    //显示第三个
                    item3.classList.remove('mui-hidden');
                    slider.gotoItem(2,300);
                    
                    var threeLi = threeUl.getElementsByTagName('li');
                    for( i=0 ; i<threeLi.length ; i++){
                        var a = threeLi[i];
                        a.index = i;
                        a.addEventListener('tap',function(){
                            for(var i = 0; i < threeLi.length ; i++){
                                threeLi[i].classList.remove('active');
                            }
                            this.classList.add('active');
                        })
                    }
                })
            }
        })
    }
    
    
    //导航点击事件
    selectNavLi[0].addEventListener('tap',function(){
        slider.gotoItem(0,300);
    })
    selectNavLi[1].addEventListener('tap',function(){
        slider.gotoItem(1,300);
    })
    selectNavLi[2].addEventListener('tap',function(){
        slider.gotoItem(2,300);
    })
    
    //提交
    document.getElementById('addressBtn').addEventListener('tap',function(){
      var provinceName = '';
      var cityName = '';
      var areaName = '';
        p = '';
        num = '';
        var active = document.getElementsByClassName('select-con')[0].getElementsByClassName('active');
        if(active.length == 0){
            return;
        }else{
          if(active.length!=3){
            mui.toast("请选择地区到区县");
            return;
          }
            var threeLi = threeUl.getElementsByClassName('active').length;
            provinceName = active[0].innerText;
            cityName = active[1].innerText;
            areaName = active[2].innerText;
            for(i=0 ; i<active.length ; i++){
                var value = active[i].getAttribute('value');
                var text = active[i].innerText;
                if(i==0){
                    num += value;
                }else{
                    num += ','+value;
                }
                if(threeLi>1){
                    p = active[0].innerText + active[1].innerText + '多个区域';
                }else{
                    p += text;
                }
            }
        }
        
        
//        赋值
        giveValue(provinceName, cityName, areaName);
    })
}