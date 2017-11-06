using System.ServiceModel;
using System.ServiceModel.Web;
using Pub_n_Fun.Models;
using System.Collections.Generic;

namespace Pub_n_Fun
{
    [ServiceContract]
    public interface IPubnFunCore
    {
        [OperationContract]
        [WebInvoke(Method = "GET", UriTemplate = "/token/{token}", ResponseFormat = WebMessageFormat.Json)]
        string GetSystemDate(string token);

        [OperationContract]
        [WebInvoke(Method = "GET", UriTemplate = "/GetPubByID={pubID}", ResponseFormat = WebMessageFormat.Json)]
        Pub GetPubByID(string pubID);

        [OperationContract]
        [WebInvoke(Method = "GET", UriTemplate = "/GetAllPub", ResponseFormat = WebMessageFormat.Json)]
        List<Pub> GetAllPubList();

        [OperationContract]
        [WebInvoke(Method = "POST", UriTemplate = "/PostPub", ResponseFormat = WebMessageFormat.Json, RequestFormat = WebMessageFormat.Json)]
        void AddPub(Pub PubToBeAdded);

        [OperationContract]
        [WebInvoke(Method = "PUT", UriTemplate = "/PutPub", ResponseFormat = WebMessageFormat.Json, RequestFormat = WebMessageFormat.Json)]
        void UpdatePub(Pub PubToBeUpdated);

        [OperationContract]
        [WebInvoke(Method = "DELETE", UriTemplate = "/DeletePubByID={pubID}", RequestFormat = WebMessageFormat.Json, ResponseFormat = WebMessageFormat.Json)]
        void DeletePubByID(string pubID);

        [OperationContract]
        [WebInvoke(Method = "GET", UriTemplate = "/GetOpinionByID={opinionID}", ResponseFormat = WebMessageFormat.Json)]
        CustomerOpinion GetCustomerOpinion(string opinionID);

        [OperationContract]
        [WebInvoke(Method = "GET", UriTemplate = "/GetAllOpinionAboutPubByID={pubID}", ResponseFormat = WebMessageFormat.Json)]
        List<CustomerOpinion> GetAllOpinionListAboutPubByID(string pubID);

        [OperationContract]
        [WebInvoke(Method = "POST", UriTemplate = "/PostOpinion", ResponseFormat = WebMessageFormat.Json, RequestFormat = WebMessageFormat.Json)]
        void AddOpinion(CustomerOpinion OpinionToBeAdded);

        [OperationContract]
        [WebInvoke(Method = "PUT", UriTemplate = "/PutOpinion", ResponseFormat = WebMessageFormat.Json, RequestFormat = WebMessageFormat.Json)]
        void UpdateOpinion(CustomerOpinion OpinionToBeUpdated);

        [OperationContract]
        [WebInvoke(Method = "DELETE", UriTemplate = "/DeleteOpinionByID={opinionID}", RequestFormat = WebMessageFormat.Json, ResponseFormat = WebMessageFormat.Json)]
        void DeleteOpinionByID(string opinionID);
    }
}
