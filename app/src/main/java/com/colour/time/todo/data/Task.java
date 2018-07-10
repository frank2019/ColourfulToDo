/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.colour.time.todo.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.colour.time.todo.NetRequest.vo.TaskRemote;
import com.google.common.base.Objects;
import com.google.common.base.Strings;
import com.google.gson.annotations.SerializedName;

import java.util.UUID;

/**
 * Immutable model class for a Task.
 */
@Entity(tableName = "tasks")
public final class Task {

    @SerializedName("id")
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private final String mId;

    @SerializedName("title")
    @Nullable
    @ColumnInfo(name = "title")
    private final String mTitle;

    @SerializedName("description")
    @Nullable
    @ColumnInfo(name = "description")
    private final String mDescription;

    @SerializedName("completed")
    @ColumnInfo(name = "completed")
    private final boolean mCompleted;

    @Nullable
    @SerializedName("ticks_expect")
    @ColumnInfo(name = "ticks_expect")
    private  Integer mTicksExpect;

    @Nullable
    @SerializedName("ticks_consume")
    @ColumnInfo(name = "ticks_consume")
    private  Integer mTicksConsume;

    @Nullable
    @SerializedName("father_id")
    @ColumnInfo(name = "father_id")
    private  Integer mFatherId;

    @Nullable
    @SerializedName("selected")
    @ColumnInfo(name = "selected")
    private  Boolean mSelected;

    @Nullable
    @SerializedName("sub_ids")
    @ColumnInfo(name = "sub_ids")
    private  String mSubIds;


    @Nullable
    @SerializedName("create_time")
    @ColumnInfo(name = "create_time")
    private  Long mCreateTime;

    @Nullable
    @SerializedName("user_id")
    @ColumnInfo(name = "user_id")
    private  Integer mUserId;

    @Nullable
    @SerializedName("four_quadrant")
    @ColumnInfo(name = "four_quadrant")
    private  String mFourQuadrantTag;

    /**
     * Use this constructor to create a new active Task.
     *
     * @param title       title of the task
     * @param description description of the task
     */
    @Ignore
    public Task(@Nullable String title, @Nullable String description) {
        this(UUID.randomUUID().toString(),title, description,  false,0,0,0,false,"",System.currentTimeMillis(),0,"Non-important_Non-Urgent");
    }





    @Ignore
    public Task(@NonNull String mId, String mTitle, String mDescription, boolean mCompleted, Integer mTicksExpect, Integer mTicksConsume, Integer mFatherId, Boolean mSelected, String mSubIds, Long mCreateTime, Integer mUserId, String mFourQuadrantTag) {
        this.mId = mId;
        this.mTitle = mTitle;
        this.mDescription = mDescription;
        this.mCompleted = mCompleted;
        this.mTicksExpect = mTicksExpect;
        this.mTicksConsume = mTicksConsume;
        this.mFatherId = mFatherId;
        this.mSelected = mSelected;
        this.mSubIds = mSubIds;
        this.mCreateTime = mCreateTime;
        this.mUserId = mUserId;
        this.mFourQuadrantTag = mFourQuadrantTag;
    }




    /**
     * Use this constructor to create an active Task if the Task already has an id (copy of another
     * Task).
     *
     * @param title       title of the task
     * @param description description of the task
     * @param id          id of the task
     */
    @Ignore
    public Task(@Nullable String title, @Nullable String description, @NonNull String id) {
        //this(title, description, id, false);
        this(id,title, description,  false,0,0,0,false,"",System.currentTimeMillis(),0,"Non-important_Non-Urgent");
    }

    /**
     * Use this constructor to create a new completed Task.
     *
     * @param title       title of the task
     * @param description description of the task
     * @param completed   true if the task is completed, false if it's active
     */
    @Ignore
    public Task(@Nullable String title, @Nullable String description, boolean completed) {
        //this(title, description, UUID.randomUUID().toString(), completed);
        this(UUID.randomUUID().toString(),title, description,  completed,0,0,0,false,"",System.currentTimeMillis(),0,"Non-important_Non-Urgent");
    }

    /**
     * Use this constructor to specify a completed Task if the Task already has an id (copy of
     * another Task).
     *
     * @param title       title of the task
     * @param description description of the task
     * @param id          id of the task
     * @param completed   true if the task is completed, false if it's active
     */
    public Task(@Nullable String title, @Nullable String description,
                @NonNull String id, boolean completed) {
        mId = id;
        mTitle = title;
        mDescription = description;
        mCompleted = completed;
    }

    @NonNull
    public String getId() {
        return mId;
    }

    @Nullable
    public String getTitle() {
        return mTitle;
    }

    @Nullable
    public String getTitleForList() {
        if (!Strings.isNullOrEmpty(mTitle)) {
            return mTitle;
        } else {
            return mDescription;
        }
    }

    @Nullable
    public String getDescription() {
        return mDescription;
    }

    public boolean isCompleted() {
        return mCompleted;
    }

    public boolean isActive() {
        return !mCompleted;
    }

    public boolean isEmpty() {
        return Strings.isNullOrEmpty(mTitle) &&
               Strings.isNullOrEmpty(mDescription);
    }

    @Nullable
    public Integer getTicksExpect() {
        return mTicksExpect;
    }

    @Nullable
    public Integer getTicksConsume() {
        return mTicksConsume;
    }

    @Nullable
    public Integer getFatherId() {
        return mFatherId;
    }

    @Nullable
    public Boolean getSelected() {
        return mSelected;
    }

    @Nullable
    public String getSubIds() {
        return mSubIds;
    }

    @Nullable
    public Long getCreateTime() {
        return mCreateTime;
    }

    @Nullable
    public Integer getUserId() {
        return mUserId;
    }

    @Nullable
    public String getFourQuadrantTag() {
        return mFourQuadrantTag;
    }


    public void setTicksExpect(@Nullable Integer mTicksExpect) {
        this.mTicksExpect = mTicksExpect;
    }

    public void setTicksConsume(@Nullable Integer mTicksConsume) {
        this.mTicksConsume = mTicksConsume;
    }

    public void setFatherId(@Nullable Integer mFatherId) {
        this.mFatherId = mFatherId;
    }

    public void setSelected(@Nullable Boolean mSelected) {
        this.mSelected = mSelected;
    }

    public void setSubIds(@Nullable String mSubIds) {
        this.mSubIds = mSubIds;
    }

    public void setCreateTime(@Nullable Long mCreateTime) {
        this.mCreateTime = mCreateTime;
    }

    public void setUserId(@Nullable Integer mUserId) {
        this.mUserId = mUserId;
    }

    public void setFourQuadrantTag(@Nullable String mFourQuadrantTag) {
        this.mFourQuadrantTag = mFourQuadrantTag;
    }

    @Override
    public int hashCode() {
        int result = mTitle.hashCode();
        result = 31 * result + mDescription.hashCode();
        result = 31 * result + (mCompleted ? 1 : 0);
        result = 31 * result + mTicksExpect.hashCode();
        result = 31 * result + mTicksConsume.hashCode();
        result = 31 * result + mFatherId.hashCode();
        result = 31 * result + mSelected.hashCode();
        result = 31 * result + mSubIds.hashCode();
        result = 31 * result + mCreateTime.hashCode();
        result = 31 * result + mUserId.hashCode();
        result = 31 * result + mFourQuadrantTag.hashCode();
        return result;
    }




    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equal(mId, task.mId) &&
               Objects.equal(mTitle, task.mTitle) &&
               Objects.equal(mDescription, task.mDescription);
    }

    /*@Override
    public int hashCode() {
        return Objects.hashCode(mId, mTitle, mDescription);
    }
*/
    @Override
    public String toString() {
        return "Task with title " + mTitle;
    }
}
