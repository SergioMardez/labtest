package com.sergiom.labaddlibrary.model;

import com.sergiom.labaddlibrary.enumeration.AdType;
import com.sergiom.labaddlibrary.enumeration.State;

public class AdDataState {
    private State state;
    private AdType adType;

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public AdType getAdType() {
        return adType;
    }

    public void setAdType(AdType adType) {
        this.adType = adType;
    }

    public AdDataState(){
        this.state = State.FAILED;
        this.adType = AdType.BANNER;
    }

    public AdDataState(State state, AdType adType){
        this.state = state;
        this.adType = adType;
    }
}
