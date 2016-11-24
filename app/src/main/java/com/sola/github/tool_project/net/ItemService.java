package com.sola.github.tool_project.net;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by slove
 * 2016/11/22.
 */
public interface ItemService {
    // ===========================================================
    // Constants
    // ===========================================================

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================

    // ===========================================================
    // Methods
    // ===========================================================

    @POST("/society/selectSocietyListByParam")
    Observable<Void> requestSomeList(@Body String entryStr);

}
