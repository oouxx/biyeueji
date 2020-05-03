<template>
  <div class="container">
    <div class="post">
      <van-form @submit="commitComment">
        <van-field
          v-model="star"
          name="评分"
          label="评分"
          placeholder="评分"
          :rules="[{ required: true, message: '请填写评分' }]"
        />
        <van-field
          v-model="content"
          name="内容"
          label="内容"
          placeholder="内容"
          :rules="[{ required: true, message: '请填写内容' }]"
        />
        <div style="margin: 16px;">
          <van-button round block type="info" native-type="submit">
            提交
          </van-button>
        </div>
      </van-form>
    </div>
    <div class="comment" v-for="item in comments" v-bind:key="item">
      <div class="info">
        <img class="avatar" :src="item.fromAvatar" width="36" height="36"/>
        <div class="right">
          <div class="name">{{item.fromName}}</div>
          <div class="date">{{item.date}}</div>
        </div>
      </div>
      <div class="content">{{item.content}}</div>
      <div class="control">
      </div>
    </div>
  </div>
</template>

<script>
  import {commentList,commentCount,commentPost} from '@/api/api';

  export default {
    components: {},
    data() {
      return {
        star: '',
        content: '',
        comments: [],
        itemId: this.$route.params.itemId
      }
    },
    computed: {},
    methods: {
        // 提交评论
      commitComment() {
        commentPost({
          type: 0,
          valueId: this.itemId,
          star: this.star,
          content: this.content
        }).then(res =>{
          console.log(res.data)
          console.log('提交成功')
        })
      },
      loadComments() {
        commentList({valueId: this.itemId, type: 0, showType: 0}).then(res => {
          console.log(res.data.data);
          var list = res.data.data.list
          for(var i = 0; i < list.length; i++ ){
            var item = list[i]
            this.comments.push({
              fromAvatar: item.userInfo.avatarUrl,
              fromName: item.userInfo.nickName,
              date: item.addTime,
              content: item.content
            })
          }
        })
      }
    },
    created() {
      this.loadComments();
    }
  }
</script>

<style lang="scss" scoped>
  .container {
    padding: 0 10px;
    box-sizing: border-box;
    .comment {
      display: flex;
      flex-direction: column;
      padding: 10px;
      border-bottom: 1px solid;
      .info {
        display: flex;
        align-items: center;
        .avatar {
          border-radius: 50%;
        }
        .right {
          display: flex;
          flex-direction: column;
          margin-left: 10px;
          .name {
            font-size: 16px;
            color: #000000;
            margin-bottom: 5px;
            font-weight: 500;
          }
          .date {
            font-size: 12px;
            color: #000000;
          }
        }
      }
      .content {
        font-size: 16px;
        color: #000000;
        line-height: 20px;
        padding: 10px 0;
      }
    }
  }
</style>
