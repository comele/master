class AppModel{
	constructor(){
		this.renderSideBar = null;
		this.classContent = null;
		this.classSideBar = null;
		this.classCloseOpenPanel = null;
		
	}
	
	showSideBar(){
		this.renderSideBar = true;
		this.classContent = 'col-md-9 middleContent';
		this.classSideBar = 'col-md-3 middleContent';
		this.classCloseOpenPanel = 'open';
	}
	
	hideSideBar(){
		this.renderSideBar = false;
		this.classContent = 'col-md-12 divPadding';
		this.classCloseOpenPanel = 'open';
	}
}
export default AppModel;
