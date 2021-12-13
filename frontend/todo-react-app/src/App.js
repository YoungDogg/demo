import React from 'react';
import Todo from './Todo';
import AddTodo from "./AddTodo.js";
import { Paper, List, Container, Grid, 
  Button, AppBar, Toolbar,Typography 
} from "@material-ui/core";
import './App.css';
import {call, signout} from "./service/ApiService"; 

class App extends React.Component{
  constructor(props){
    super(props);
    this.state = {
      items: [
        // { id: "0", title : "Hello World 1", done: true},
        // { id: "1", title : "Hello World 2", done: false}, 
      ],  
      // 1. 로딩 중이라는 상태를 표현할 변수 생성자에 상태변수 추가
      loading: true,
    };
  }
  componentDidMount(){
    // 2. componentDidMount에서 Todo 리스트를 가져오는 GET 요청이 성공적으로 리턴한다면
    // loading을 false로 고친다. 로딩이 아니라는 뜻 
    call("/todo", "GET", null).then((response) =>
      this.setState({ items: response.data, loading:false})
    );
  }
  // componentDidMount(){
  //   const requestOptions = {
  //     method : "GET",
  //     headers:{ "Content-Type": "application/json"}
  //   };

  //   fetch("http://localhost:8080/todo", requestOptions)
  //   .then((response) => response.json())
  //   .then(
  //     (response) => {
  //       this.setState({
  //         items: response.data
  //       });
  //     },
  //     (error) => {
  //       this.setState({
  //         error
  //       });
  //     }
  //   );
  // }

  // 1. 함수 추가
  add = (item) => {
    // const thisItems = this.state.items;
    // item.id = "ID-" + thisItems.length; // key를 위한 id 추가
    // item.done = false; // done 초기화
    // thisItems.push(item); // list에 아이템 추가
    // this.setState({ items: thisItems}); // 업데이트는 반드시 this.setState로 해야됨
    // console.log("items : ", this.state.items);
    call("/todo", "POST", item).then((response) =>
    this.setState({items : response.data}));
  };

  delete = (item) => {
    // console.log("Before delete items : ", this.state.items);
    // const thisItems = this.state.items;
    // const newItems = thisItems.filter(e => e.id !== item.id);
    // this.setState({ items: newItems}, () => {
    //   // 디버깅 콜백
    //   console.log("Update Items : ", this.state.items)
    // }); 
    call("/todo", "DELETE", item).then((response) => 
    this.setState({ items : response.data}));
  } ;

  update = (item) => {
    call("/todo", "PUT", item).then((response) =>
      this.setState({ items : response.data})
    );
  };

  render(){
    var todoItems = this.state.items.length > 0 && (
      <Paper style={{ margin: 16}}>
        <List>
          {this.state.items.map((item, idx) => (
            <Todo 
            item={item}
            key={item.id}
            delete={this.delete}
            update={this.update}
            />
          ))}
        </List>
      </Paper>
    );
    
    // navigationBar 추가
    var navigationBar = (
      <AppBar position='static'>
        <Toolbar>
          <Grid justifyContent='space-between' container>
            <Grid item>
              <Typography variant='h6'>TODO LIST</Typography>
            </Grid>
            <Grid>
              <Button color="inherit" onClick={signout}>
                로그아웃
              </Button>
            </Grid>
          </Grid>
        </Toolbar>
      </AppBar>
    )

    // 로딩 중이 아닐 때 렌더링할 부분
    var todoListPage = (
      <div>
        {navigationBar}
        <Container maxWidth="md">
          <AddTodo add={this.add}/>
          <div className="TodoList">{todoItems}</div>
        </Container>
      </div>
    );

    // 로딩 중일 때 렌더링할 부분
    var loadingPage = <h1>Loading...</h1>;

    var content = loadingPage;

    if(!this.state.loading){
      // not loading
      content = todoListPage;
    }

    return <div className="App">{content}</div>
     
    // // 2. 자바스크립트가 제공하는 map 함수를 이용한다.
    // var todoItems = this.state.items.map((item, idx)=>(
    //   <Todo item={item} idx={item.id}/>
    // ));
    // return(
    //   <div className="App">
    //     {/* <Todo item={todoItems} />   */}
    //     {todoItems}
    //   </div>
    // );

    // return <div className="App">{todoItems}</div>
    // return (
    //   <div className="App">
    //     {navigationBar} {/* 네이게이션바 렌더링 */} 
    //     <Container maxWidth="md">
    //       <AddTodo add={this.add} />
    //       <div className="TodoList">{todoItems}</div>
    //     </Container>
    //   </div>
    // );
  }
}

// function App() {
//   return (
//     <div className="App">
//       <header className="App-header">
//         <img src={logo} className="App-logo" alt="logo" />
//         <p>
//           Edit <code>src/App.js</code> and save to reload.
//         </p>
//         <a
//           className="App-link"
//           href="https://reactjs.org"
//           target="_blank"
//           rel="noopener noreferrer"
//         >
//           Learn React
//         </a>
//       </header>
//       <Todo/>
//     </div>
//   );
// }



export default App;
