import { createSlice } from "@reduxjs/toolkit";
import axios from 'axios';
import { toast } from "react-toastify";
const initialState = {
    hierarchy: [],
    isLoadingHeirarchy: false,
    hierarchyError: null,
}

const notify = (msg) => toast(msg);
export const getHeirarchyData = () =>{
    return async(dispatch) =>{
        try {
          dispatch(fetchingHierarchyRequest());
          const response = await axios.get('http://localhost:8080/hierarchy/reporting');
          const data = response.data;
          dispatch(fetchingHierarchySuccess(data));
        } catch (error) {
          dispatch(fetchingHierarchyFailure(error));
        }
    }
}

export const updateHierarchy = (updateRequest) =>{
    return async(dispatch) =>{
        try {
            console.log("Inside update ",updateRequest);
            dispatch(fetchingHierarchyRequest());
            const response = await axios.put(`http://localhost:8080/hierarchy/update-reporting-hierarchy`,updateRequest);
             notify(response.data.message);
             dispatch(updateSuccess());
        } catch (error) {
            if(error.response)
            {
                notify(error.response.data.message);
            }
          dispatch(fetchingHierarchyFailure(error));
        }
    }
}



export const getHeirarchyIdData = (index) =>{
    return async(dispatch) =>{
        try {
          dispatch(fetchingHierarchyRequest());
          const response = await axios.get(`http://localhost:8080/hierarchy/reporting/${index}`);
          const data = response.data;
          dispatch(fetchingHierarchySuccess(data));
        } catch (error) {
          dispatch(fetchingHierarchyFailure(error));
        }
    }
}
const heirarchySlice = createSlice({
    name : 'hierarchy',
    initialState,
    reducers : {
        fetchingHierarchyRequest : (state) =>{
            state.isLoadingHeirarchy = true;
            state.hierarchyError = null;
        },
        fetchingHierarchySuccess : (state, action) =>{
            state.hierarchy = action.payload;
            state.isLoadingHeirarchy = false;
            state.hierarchyError = null;
        },
        fetchingHierarchyFailure : (state, action) =>{
            state.hierarchy = [];
            state.isLoadingHeirarchy = false;
            state.hierarchyError = action.payload;
        },
        updateSuccess : (state, action) =>{
            state.isLoadingHeirarchy = false;
            state.hierarchyError = null;
        }
    }
})
export const {
    fetchingHierarchyRequest, fetchingHierarchySuccess, fetchingHierarchyFailure,updateSuccess
} = heirarchySlice.actions;
export default heirarchySlice.reducer;
  
