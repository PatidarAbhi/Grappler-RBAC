import { createSlice } from "@reduxjs/toolkit";
import axios from 'axios';
import { notify } from "../Components/Toastify.js"
import Swal from "sweetalert2";
const initialState = {
    tickets: [],
    isLoadingTickets: false,
    ticketsError: null,
}
export const getTicketsByUserId = (userId) => {
    return async (dispatch) => {
        try {
            dispatch(fetchingDataRequest());
            const response = await axios.get(`http://localhost:8080/tickets/user/${userId}`);
            dispatch(fetchingDataSuccess(response.data));
            return response.data;
        } catch (error) {
            if (error.response) {
                notify(error.response.data.message);
            }
            dispatch(fetchingDataFailure(error));
        }
    }
}

export const getAllTickets = () => {
    return async (dispatch) => {
        try {
            dispatch(fetchingDataRequest());
            const response = await axios.get(`http://localhost:8080/tickets/`);
            dispatch(fetchingDataSuccess(response.data));
            return response.data;
        } catch (error) {
            if (error.response) {
                notify(error.response.data.message);
            }
            dispatch(fetchingDataFailure(error));
        }
    }
}

export const addTicketFun = (ticket) => {
    return async (dispatch) => {
        try {
            dispatch(fetchingDataRequest());
            const response = await axios.post(`http://localhost:8080/tickets/`,ticket);
            const data = response.data.data;
            dispatch(addingDataSuccess(data));
            return response.data;
        } catch (error) {
            if (error.response) {
                notify(error.response.data.message);
            }
            dispatch(fetchingDataFailure(error));
        }
    }
}


const TicketSlice = createSlice({
    name: 'TickettSlice',
    initialState,
    reducers: {
        fetchingDataRequest: (state) => {
            state.isLoadingTickets=true;
            state.ticketsError = null;
        },
        fetchingDataSuccess: (state, action) => {
            state.tickets = action.payload;
            state.isLoadingTickets=false;
            state.ticketsError = null;
        },
        fetchingDataFailure: (state, action) => {
            state.tickets = [];
            state.isLoadingTickets=false;
            state.ticketsError = action.payload;
        },
        addingDataSuccess :(state,action)=>
        {
            
        }
    }
})
export const {
    fetchingDataRequest, fetchingDataSuccess, fetchingDataFailure,addingDataSuccess
} = TicketSlice.actions;
export default TicketSlice.reducer;

