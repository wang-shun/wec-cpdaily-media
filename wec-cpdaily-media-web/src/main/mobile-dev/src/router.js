
// 今日校园内部校园号申请页面相关
import MobileMediaList from '@/pages/mobileapply/MobileMediaList'
import mediaDeny from '@/pages/mobileapply/mediaDeny'
import MobileSelectIdentity from '@/pages/mobileapply/MobileSelectIdentity'
import MobileApply from '@/pages/mobileapply/MobileApply'
import Protocol from '@/pages/mobileapply/Protocol'
import MediaList from '@/pages/medialist/MediaList'

export default new VueRouter({
    routes: [
        {
            path: '/',
            redirect: '/mobilemedialist'
        },
        {
            path: '/mobilemedialist',
            name: 'mobilemedialist',
            component: MobileMediaList,
            meta: {
                title: '申请校园号'
            }
        },
        {
            path: '/mediadeny',
            name: 'mediadeny',
            component: mediaDeny,
            meta: {
                title: '提示'
            }
        },
        {
            path: '/mobileselectidentity',
            name: 'mobileselectidentity',
            component: MobileSelectIdentity,
            meta: {
                title: '身份类型选择'
            }
        },
        {
            path: '/mobileapply',
            name: 'mobileapply',
            component: MobileApply,
            meta: {
                title: '申请校园号'
            }
        },
        {
            path: '/protocol',
            name: 'protocol',
            component: Protocol,
            meta: {
                title: '用户协议'
            }
        },
        {
            path: '/medialist',
            name: 'medialist',
            component: MediaList,
            meta: {
                title: '校园影响力排行'
            }
        }
    ]
})
