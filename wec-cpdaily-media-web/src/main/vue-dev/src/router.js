import Login from '@/pages/login/Login'
import Main from '@/pages/Main'
import Main2 from '@/pages/Main2'
import Group from '@/pages/group/group'
import NewGroup from '@/pages/group/new-group/newGroup'
import AddUser from '@/pages/group/add-user/addUser'
import ViewGroup from '@/pages/group/view-group/viewGroup'
import Message from '@/pages/message/message'
import PublishMessage from '@/pages/message/publish-message/publishMessage'
import ActivityList from '@/pages/activity/ActivityList'
import AddNewActivity from '@/pages/activity/AddNewActivity'

import OuterMain from '@/pages/outer/OuterMain'
import MediaList from '@/pages/outer/MediaList'
import ViewAdmins from '@/pages/outer/ViewAdmins'
import ApproveDesc from '@/pages/outer/ApproveDesc'
import ApplyMedia from '@/pages/outer/ApplyMedia'
import Protocol from '@/pages/outer/Protocol'
import MediaInfo from '@/pages/outer/MediaInfo'
import ReplySetting from '@/pages/reply/replySetting'
import Logs from '@/pages/logs/Logs'
import MsgView from '@/pages/MsgView/MsgView'
import Kuaida from '@/pages/kuaida/Kuaida'


export default new VueRouter({
    routes: [
        {
            path: '/home',
            name: 'home',
            component: Main,
            redirect: '/home/message',
            children: [
                {
                    path: 'message',
                    name: 'message',
                    component: Message
                },
                {
                    path: 'publishmessage/:type/:messageId',
                    name: 'publishmessage',
                    component: PublishMessage
                },
                {
                    path: 'activitylist',
                    name: 'activitylist',
                    component: ActivityList
                },
                {
                    path: 'addnewactivity',
                    name: 'addnewactivity',
                    component: AddNewActivity
                },
                {
                    path: 'group',
                    name: 'group',
                    component: Group
                },
                {
                    path: 'newgroup',
                    name: 'newgroup',
                    component: NewGroup
                },
                {
                    path: 'adduser',
                    name: 'adduser',
                    component: AddUser
                },
                {
                    path: 'viewgroup',
                    name: 'viewgroup',
                    component: ViewGroup
                },
                {
                    path: 'replysetting',
                    name: 'replysetting',
                    component: ReplySetting
                },
                {
                    path: 'logs',
                    name: 'logs',
                    component: Logs
                },
                {
                    path: 'kuaida',
                    name: 'kuaida',
                    component: Kuaida
                }
            ]
        },
        {
            path: '/',
            redirect: '/outer'
        },
        {
            path: '/login',
            name: 'Login',
            component: Login,
        },
        {
            path: '/login/:type',
            name: 'Login',
            component: Login,
        },
        {
            path: '/msgview/:msgId',
            name: 'msgview',
            component: MsgView,
        },
        {
            path: '/home2',
            name: 'home2',
            component: Main2,
        },
        {
            path: '/outer',
            name: 'outer',
            component: OuterMain,
            children: [
                {
                    path: '',
                    name: '',
                    component: MediaList
                },
                {
                    path: 'mediainfo/:mediaId',
                    name: '',
                    component: MediaInfo
                },
                {
                    path: 'medialist',
                    name: 'medialist',
                    component: MediaList
                },
                {
                    path: 'approvedesc/:mediaId',
                    name: 'approvedesc',
                    component: ApproveDesc
                },
                {
                    path: 'applymedia',
                    name: 'applymedia',
                    component: ApplyMedia
                },
                {
                    path: 'protocol',
                    name: 'protocol',
                    component: Protocol
                },
                {
                    path: 'viewadmins',
                    name: 'viewadmins',
                    component: ViewAdmins
                }
            ]
        }
    ]
})
