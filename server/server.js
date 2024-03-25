const express = require('express');
const dotenv = require('dotenv');

dotenv.config();
const app = express()

const server = require('http').createServer(app);
const io = require('socket.io')(server);

io.on('connection', client =>{
    console.log(`connection received`);
    client.on(`new_message`,(chat)=>{
        console.log(`new message received ${chat}`);
        io.emit('broadcast',chat);
    })
})

app.get('/', (req,resp) =>{
    resp.send('Server is running')
});


const port = process.env.PORT;
server.listen(port,()=>{
    console.log(`Server running at ${port}`)
});