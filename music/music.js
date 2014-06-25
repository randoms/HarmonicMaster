var uuid = require('node-uuid');
var fs = require('fs')

var data = {
    author:'',
    time:'',
    name:'',
    id:'',
    sounds:[],
    length:0,
}

function create(author,name){
    data.author = author,
    data.id = uuid.v1()
    data.name = name
}

// add sound to Sheet Music, 每一时刻只能允许有一个音发出, unit milliseconds
// 这里做好能把音做成name的形式，具体的数字用内部转换
// -1 是空白

var space = 0;
function add(sound,time){
    if(sound == -1){
        space = time
        return
    }
    
    var start = 0
    if(data.sounds.length != 0){
        start = data.sounds[data.sounds.length -1]['end']+space
        space = 0
    }
    
    data.sounds.push({
        name:sound,
        start:start,
        end:start+time,
    })
}


// gene json type Sheet Music
function gene(){
    data.length = 0
    if(data.sounds.length != 0){
        data.length = data.sounds[data.sounds.length-1]['end']
    }
    data.time = new Date().valueOf()
    fs.writeFile(data.name+'.json',JSON.stringify(data,null,4),function(err){
        if(err)console.log(err);
        else console.log("complete");
    })
}

module.exports.create = create
module.exports.add = add
module.exports.gene = gene